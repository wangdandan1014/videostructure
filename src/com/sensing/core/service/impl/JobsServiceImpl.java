package com.sensing.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sensing.core.bean.Jobs;
import com.sensing.core.bean.JobsChannel;
import com.sensing.core.bean.JobsStateLog;
import com.sensing.core.bean.JobsTemplateDb;
import com.sensing.core.bean.SysSubscribe;
import com.sensing.core.bean.SysUser;
import com.sensing.core.bean.TemplateObjMotor;
import com.sensing.core.bean.job.req.JobListCountReq;
import com.sensing.core.bean.job.req.UpdateJobReq;
import com.sensing.core.bean.job.req.UpdateOperateReq;
import com.sensing.core.bean.job.resp.JobAlarmCount;
import com.sensing.core.bean.job.resp.JobListCountResp;
import com.sensing.core.bean.job.resp.JobListResp;
import com.sensing.core.bean.job.resp.JobRatifyListResp;
import com.sensing.core.bean.message.SysMessageLog;
import com.sensing.core.controller.AlarmDataPush;
import com.sensing.core.dao.IAlarmDAO;
import com.sensing.core.dao.IJobsChannelDAO;
import com.sensing.core.dao.IJobsDAO;
import com.sensing.core.dao.IJobsStateLogDAO;
import com.sensing.core.dao.ISysMessageLogDAO;
import com.sensing.core.dao.ISysSubscribeDAO;
import com.sensing.core.dao.ISysUserDAO;
import com.sensing.core.dao.ITemplateDbDAO;
import com.sensing.core.dao.ITemplateObjMotorDAO;
import com.sensing.core.service.IJobsService;
import com.sensing.core.service.ITemplateObjMotorService;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.ResponseBean;
import com.sensing.core.utils.StringUtils;
import com.sensing.core.utils.UuidUtil;
import com.sensing.core.utils.results.ResultUtils;
import com.sensing.core.utils.task.JobsAndTaskTimer;
import com.sensing.core.utils.task.JobsTimerTask;

/**
 * @author wenbo
 */
@Service
public class JobsServiceImpl implements IJobsService {

	private static final Log log = LogFactory.getLog(IJobsService.class);

	@Resource
	public IJobsDAO jobsDAO;
	@Resource
	public IJobsChannelDAO jobsChannelDAO;
	@Resource
	public ISysSubscribeDAO sysSubscribeDAO;
	@Resource
	public TaskServiceImpl taskServiceImpl;
	@Resource
	public ITemplateObjMotorService templateObjMotorService;
	@Resource
	public ITemplateObjMotorDAO templateObjMotorDAO;
	@Resource
	public JobsTimerTask jobsTimerTask;
	@Resource
	public IAlarmDAO alarmDAO;
	@Resource
	public ISysMessageLogDAO sysMessageLogDAO;
	@Resource
	public JobsAndTaskTimer jobsAndTaskTimer;
	@Resource
	public IJobsStateLogDAO jobsStateLogDAO;
	@Resource
	public ISysUserDAO sysUserDAO;
	@Resource
	public ITemplateDbDAO templateDbDAO;

	/**
	 * 消息模块总结 type 类型 1：申请列表的审批通过；2：申请列表的审批不通过； 3：审批列表的待审批 -----来源------------- +1
	 * ---------------------------------------- -1 ------ 申请列表（0）： 审批操作以后 ratifyJob
	 * 审批列表的审批通过或者不通过的详情 info 审批列表（1）： 保存布控 save 撤控 updateOperate 审批列表点击进审批详情 info
	 */

	public JobsServiceImpl() {
		super();
	}

	@Override
	public ResponseBean saveNewJobs(Jobs jobs) {
		// 名称去重
		int count = jobsDAO.getJobsByJobName(jobs.getName(), null);

		if (count > 0) {
			return new ResponseBean(-1, "已存在名称为" + jobs.getName() + "的布控任务");
		}
		
		//判断目标库是否存在，不存在的情况下，返回给客户端错误的信息
		try {
			List<Integer> templatedbIds = jobs.getTemplatedbIds();
			int selectDbCount = templateDbDAO.selectDbCount(templatedbIds);
			if ( templatedbIds.size() != selectDbCount ) {
				return new ResponseBean(-1,"保存失败，刷新页面请确定模板库是否存在");
			}
		} catch (Exception e) {
			log.error("判断目标库是否存在，不存在的情况下，返回给客户端错误的信息。发生异常："+e.getMessage());
			e.printStackTrace();
		}
		//判断单目标库下的车辆是否存在，不存在的情况下，给客户端提示信息
		try {
			if ( StringUtils.isNotEmpty(jobs.getObj_uuid()) ) {
				Pager pager = new Pager();
				pager.getF().put("uuid",jobs.getObj_uuid());
				pager.getF().put("isDeleted",Constants.DELETE_NO+"");
				List<TemplateObjMotor> objMotorList = templateObjMotorDAO.queryList(pager);
				if ( objMotorList == null || objMotorList.size() == 0  ) {
					return new ResponseBean(-1,"保存失败，刷新页面请确定单目标车辆是否存在");
				}
			}
		} catch (Exception e) {
			log.error("判断单目标库下的车辆是否存在。发生异常："+e.getMessage());
			e.printStackTrace();
		}
		
		String uuid = UuidUtil.getUuid();
		jobs.setUuid(uuid);

		/**** 设置布控状态 新建的任务状态默认待启动 ****/
		jobs.setState(Constants.JOB_STATE_WAITSTART);
		jobs.setJobsType(Constants.CAP_ANALY_TYPE_MOTOR_VEHICLE + "");
		jobsDAO.saveJobs(jobs);

		/**** 新增通道和模板库 ****/
		addJobsChannel(jobs);
		addJobsTemplete(jobs);

		// 消息
		sysMessageLogDAO
				.insert(new SysMessageLog(Constants.MSG_SHENPI_WAITFOR, uuid, jobs.getRatifyUser(), 0, 0, new Date()));
		try {
			SysUser ratifyUser = sysUserDAO.getSysUser(jobs.getRatifyUser());
			SysUser createUser = sysUserDAO.getSysUser(jobs.getCreateUser());
			// 布控任务的日志
			jobsStateLogDAO.saveJobsStateLog(
					new JobsStateLog(null, (createUser == null ? "" : createUser.getUsername()) + "发起布控申请", uuid,
							Constants.JOBS_RATIFY_RESULT_APPLY, "", jobs.getCreateUser(), new Date()));
			jobsStateLogDAO.saveJobsStateLog(
					new JobsStateLog(null, (ratifyUser == null ? "" : ratifyUser.getUsername()) + "待审批", uuid,
							Constants.JOBS_RATIFY_RESULT_WAIT, "", jobs.getCreateUser(), new Date()));
		} catch (Exception e) {
			log.error("布控任务的日志保存发生异常,异常信息:" + e.getMessage());
			e.printStackTrace();
		}
		return ResultUtils.success();
	}

	/**
	 * 编辑页面的修改 2018/12/10 只有审批不通过的，才允许修改,此处修改时不会影响布控的状态的
	 *
	 * @param jobs
	 * @return
	 */
	@Override
	public ResponseBean updateCommon(Jobs jobs) {
		// 名称去重
		int count = jobsDAO.getJobsByJobName(jobs.getName(), jobs.getUuid());
		if (count > 0) {
			return new ResponseBean(-1, "已存在名称为" + jobs.getName() + "的布控任务");
		}
		// 修改后默认进入待审批页面
		jobs.setRatifyResult(0);
		jobs.setRatifyMemo("");
		jobs.setRatifyType(0);
		jobs.setState(Constants.JOB_STATE_WAITSTART);
		jobsDAO.updateCommon(jobs);

		try {
			SysUser ratifyUser = sysUserDAO.getSysUser(jobs.getRatifyUser());
			SysUser createUser = sysUserDAO
					.getSysUser(jobs.getCreateUser() == null ? jobs.getModifyUser() : jobs.getCreateUser());
			// 布控任务的日志
			jobsStateLogDAO.saveJobsStateLog(
					new JobsStateLog(null, (createUser == null ? "" : createUser.getUsername()) + "发起布控申请",
							jobs.getUuid(), Constants.JOBS_RATIFY_RESULT_APPLY, "", createUser.getUuid(), new Date()));
			jobsStateLogDAO.saveJobsStateLog(
					new JobsStateLog(null, (ratifyUser == null ? "" : ratifyUser.getUsername()) + "待审批", jobs.getUuid(),
							Constants.JOBS_RATIFY_RESULT_WAIT, "", createUser.getUuid(), new Date()));
		} catch (Exception e) {
			log.error("布控任务的日志保存发生异常,异常信息:" + e.getMessage());
			e.printStackTrace();
		}

		/**** 移除通道和模板库 ****/
		if (!CollectionUtils.isEmpty(jobs.getChannelUuIds())) {
			jobsChannelDAO.removeJobsChannel(jobs.getUuid());
			addJobsChannel(jobs);
		}
		if (!CollectionUtils.isEmpty(jobs.getTemplatedbIds())) {
			jobsDAO.removeTempleteChannel(jobs.getUuid());
			addJobsTemplete(jobs);
		}

		// 消息
		setMsgReadByExtendId(jobs.getUuid());
		sysMessageLogDAO.insert(new SysMessageLog(Constants.MSG_SHENPI_WAITFOR, jobs.getUuid(), jobs.getRatifyUser(), 0,
				0, new Date()));

		return ResultUtils.success();
	}

	/**
	 * 将之前未读的消息都置成已读
	 *
	 * @param jobs
	 */
	private void setMsgReadByExtendId(String jobUuid) {
		if (StringUtils.isEmptyOrNull(jobUuid)) {
			return;
		}
		List<SysMessageLog> unRedsMsg = sysMessageLogDAO.getUnReadByExtengId(Arrays.asList(jobUuid));
		if (!CollectionUtils.isEmpty(unRedsMsg)) {
			sysMessageLogDAO.setMsgReadById(unRedsMsg.stream().map(a -> a.getId()).collect(Collectors.toList()));
		}
	}

	/**
	 * 添加布控关联的通道
	 *
	 * @param jobs
	 */
	public void addJobsChannel(Jobs jobs) {
		if (CollectionUtils.isEmpty(jobs.getChannelUuIds())) {
			return;
		}
		List<JobsChannel> channelList = new ArrayList<>();
		JobsChannel c = null;
		List<String> channelUuIds = jobs.getChannelUuIds();
		for (String channelUuid : channelUuIds) {
			c = new JobsChannel();
			c.setUuid(UuidUtil.getUuid());
			c.setJobUuid(jobs.getUuid());
			c.setChannelUuid(channelUuid);
			c.setCreateUser(jobs.getCreateUser());
			channelList.add(c);
		}
		// 2018/11/15 lxh 保存关联通道
		jobsChannelDAO.saveJobsChannelBetch(channelList);

	}

	/**
	 * 添加布控关联的通道
	 *
	 * @param jobs
	 */
	public void addJobsTemplete(Jobs jobs) {
		// 2018/12/10 lxh 校验模板库是否启用
//        templateObjMotorDAO.queryTemplateObjMotor()
		if (CollectionUtils.isEmpty(jobs.getTemplatedbIds())) {
			return;
		}
		List<JobsTemplateDb> list = new ArrayList<>();
		JobsTemplateDb c = null;
		List<Integer> templatedbIds = jobs.getTemplatedbIds();
		for (Integer id : templatedbIds) {
			c = new JobsTemplateDb();
			c.setUuid(UuidUtil.getUuid());
			c.setJob_uuid(jobs.getUuid());
			if (id == null) {
				continue;
			}
			c.setTemplatedb_id(id);
			c.setObj_uuid(jobs.getObj_uuid());
			list.add(c);
		}
		if (!CollectionUtils.isEmpty(list)) {
			jobsDAO.saveJobsTempleteBetch(list);
		}

	}

	@Override
	public Jobs findJobsById(java.lang.String uuid) {

		return jobsDAO.getJobs(uuid);

	}

	@Override
	public void removeJobs(String uuid) {
		jobsDAO.removeJobs(uuid);

	}

	@Override
	public Pager jobList(Pager pager) {

		List<JobListResp> list;
		int totalCount;
		if (StringUtils.isEmptyOrNull(pager.getF().get("name"))) {
			list = jobsDAO.jobList(pager);
			totalCount = jobsDAO.jobListCount(pager);
		} else {
			// 有名字的查询 sql 较慢
			list = jobsDAO.jobListByName(pager);
			totalCount = jobsDAO.jobListByNameCount(pager);
		}
		if (!CollectionUtils.isEmpty(list)) {
			List<JobAlarmCount> countMapList = alarmDAO
					.getAlarmCountByJobsUuId(list.stream().map(a -> a.getUuid()).collect(Collectors.toList()));
			List<String> singleList = list.stream().filter(a -> "单目标库".equals(a.getTemplatename()))
					.map(b -> b.getUuid()).collect(Collectors.toList());
			List<Map<String, String>> plateNoByJobUuid = null;
			if (!CollectionUtils.isEmpty(singleList)) {
				plateNoByJobUuid = jobsDAO.getPlateNoByJobUuid(singleList);
			}
			for (JobListResp t : list) {
				String str = "";
				if (StringUtils.isEmptyOrNull(t.getRunWeek())) {
					str += "每天" + "\n";
				} else {
					str += StringUtils.getWeekString(t.getRunWeek(), "、") + "\n";
				}
				// 如果是永久任务则beginDate和endDate是为空的，都有值则是按时间段执行的任务
				if (t.getEndDate() == null) {
					str += "永久任务\n";
					str += t.getBeginDateStr() + " " + t.getBeginTime() + " ~ " + t.getEndTime() + "\n";
				} else if (t.getBeginDate() != null && t.getEndDate() != null) {
					str += t.getBeginDateStr() + "~" + t.getEndDateStr() + "\n" + t.getBeginTime() + "~"
							+ t.getEndTime();
				}
				t.setJobContent(str);

				// 报警数量
				List<JobAlarmCount> job_uuid = countMapList.stream().filter(b -> b.getJobUuid().equals(t.getUuid()))
						.collect(Collectors.toList());
				if (!CollectionUtils.isEmpty(job_uuid)) {
					t.setAlarmCount(job_uuid.get(0).getCount());
				}
				// 模板库显示
				if ("单目标库".equals(t.getTemplatename()) && !CollectionUtils.isEmpty(plateNoByJobUuid)) {

					Map<String, String> map = plateNoByJobUuid.stream()
							.filter(a -> t.getUuid().equals(a.get("job_uuid"))).collect(Collectors.toList()).get(0);
					String plateNo = map.get("plate_no");
					if (StringUtils.isNotEmpty(plateNo)) {
						String newPlateNo = plateNo.replace("_", "?").replaceAll("\\%", "*");
						t.setTemplatename(t.getTemplatename() + "\\" + newPlateNo);
					}
				}
			}
		}

		pager.setTotalCount(totalCount);
		pager.setResultList(list);

		return pager;
	}

	/**
	 * 查询布控列表简单属性
	 *
	 * @return
	 */
	@Override
	public ResponseBean jobListCommon() {
		List<Jobs> list = jobsDAO.jobListCommon();
		return ResultUtils.success(list);
	}

	/**
	 * @param pager querytype 0：审批列表：只能看到审批人为当前用户的 1：申请列表：当前用户自己的创建的 ratifyResult
	 * @return
	 */
	@Override
	public Pager ratifyList(Pager pager) {
		List<JobRatifyListResp> list = jobsDAO.ratifyList(pager);
		int count = jobsDAO.ratifyListCount(pager);
		pager.setResultList(list);
		pager.setTotalCount(count);

		if (!CollectionUtils.isEmpty(list)) {
			// isRead 类型 1：申请列表的审批通过；2：申请列表的审批不通过； 3：审批列表的待审批
			List<String> jobUuidList = list.stream().map(j -> j.getJobUuid()).collect(Collectors.toList());
			List<SysMessageLog> unReadMsg = sysMessageLogDAO.getUnReadByExtengId(jobUuidList);
			list.stream().forEach(j -> {
				// 申请
				if ("1".equals(pager.getF().get("querytype") + "")) {
					List<String> list1 = unReadMsg.stream()
							.filter(m -> m.getType() == Constants.MSG_SHENQING_PASS
									|| m.getType() == Constants.MSG_SHENQING_NOPASS)
							.map(a -> a.getExtendUuId()).collect(Collectors.toList());

					if (list1.contains(j.getJobUuid())) {
						j.setIsRead(0);
					} else {
						j.setIsRead(1);
					}
					// 审批
				} else if ("0".equals(pager.getF().get("querytype") + "")) {
					List<String> list1 = unReadMsg.stream().filter(m -> m.getType() == Constants.MSG_SHENPI_WAITFOR)
							.map(a -> a.getExtendUuId()).collect(Collectors.toList());
					if (list1.contains(j.getJobUuid())) {
						j.setIsRead(0);
					} else {
						j.setIsRead(1);
					}
				}
			});
		}

		return pager;
	}

	@Override
	public int selectTemplatedbIdCount(Integer templatedbId) {
		int count = jobsDAO.selectTemplatedbIdCount(templatedbId);
		return count;
	}

	/**
	 * 布控：列表页的操作，暂停开启布控任务，取消订阅，删除，申请撤控
	 *
	 * @param
	 * @return
	 */
	@Override
	public ResponseBean updateOperate(UpdateOperateReq req) {

		if (req.getState() != null) {
			Jobs jobs = jobsDAO.getJobs(req.getJobUuid());
			// 要暂停
			if (req.getState() == Constants.JOB_STATE_PAUSE) {
				jobsAndTaskTimer.startJobWithPre(req.getJobUuid(),false);
			}
			// 要开启
			if (req.getState() == Constants.JOB_STATE_INREST && jobs.getRatifyResult() == 1) {
				jobsDAO.updateOperate(req);
				jobsAndTaskTimer.startJobs();
			}

		}
		/**** 删除 ****/
		if (req.getIsDeleted() != null && req.getIsDeleted() == 1) {
			jobsDAO.updateOperate(req);
			/**** 移除通道和模板库 ****/
			jobsChannelDAO.removeJobsChannel(req.getJobUuid());
			jobsDAO.removeTempleteChannel(req.getJobUuid());

			//  2018/12/18 lxh 删除布控是要把关联的消息也删除
			setMsgReadByExtendId(req.getJobUuid());

		}

		/**** 订阅 ****/
		if (req.getSubscribeType() != null) {
			List<SysSubscribe> sysSubscribes = sysSubscribeDAO.queryByParam(req.getUserUuid(), req.getJobUuid(), null);

			SysSubscribe ss = new SysSubscribe();
			ss.setUuid(UuidUtil.getUuid());
			ss.setJobId(req.getJobUuid());
			ss.setUid(req.getUserUuid());
			if (req.getSubscribeType().intValue() == 1) {
				// 订阅
				if (!CollectionUtils.isEmpty(sysSubscribes)) {
					return ResultUtils.error(-1, "当前已经是订阅状态，无法再次订阅");
				}
				sysSubscribeDAO.saveSysSubscribe(ss);
			} else {
				// 取消订阅
				if (CollectionUtils.isEmpty(sysSubscribes)) {
					return ResultUtils.error(-1, "当前已经是取消订阅状态，无法再次取消订阅");
				}
				sysSubscribeDAO.removeSysSubscribeByUidAndJobId(ss);
			}
			// 更新订阅缓存
			CopyOnWriteArraySet<AlarmDataPush> alarmWbSockets = AlarmDataPush.alarmWbSockets;
			for (AlarmDataPush a : alarmWbSockets) {
				if (req.getUserUuid().equals(a.userUuid)) {
					a.updateSubData();
				}
			}

		}
		/**** 撤控 ****/
		if (req.getCancelJobs() != null) {
			if (StringUtils.isEmptyOrNull(req.getCancelJobReason())) {
				return ResultUtils.error(-1, "撤控理由不能为空");
			}
			req.setRatifyResult(0);
//            审批类型 0：布控  1：撤控
			req.setRatifyType(1);
			req.setRatifyMemo("");
			jobsDAO.updateOperate(req);
			Jobs jobs = jobsDAO.getJobs(req.getJobUuid());
			try {
				SysUser createrUser = sysUserDAO.getSysUser(req.getUserUuid());
				SysUser ratifyUser = sysUserDAO.getSysUser(jobs.getRatifyUser());
				jobsStateLogDAO.saveJobsStateLog(new JobsStateLog(null,
						(createrUser == null ? "" : createrUser.getUsername()) + "发起撤控申请", req.getJobUuid(),
						Constants.JOBS_RATIFY_RESULT_APPLY, req.getCancelJobReason(), req.getUserUuid(), new Date()));
				jobsStateLogDAO.saveJobsStateLog(new JobsStateLog(null,
						(ratifyUser == null ? "" : ratifyUser.getUsername()) + "待审批", req.getJobUuid(),
						Constants.JOBS_RATIFY_RESULT_WAIT, "", req.getUserUuid(), new Date()));
			} catch (Exception e) {
				log.error("布控任务的日志保存发生异常,异常信息:" + e.getMessage());
				e.printStackTrace();
			}

			// 消息
			setMsgReadByExtendId(jobs.getUuid());
			SysMessageLog message = new SysMessageLog(Constants.MSG_SHENPI_WAITFOR, req.getJobUuid(),
					jobs.getRatifyUser(), 0, 0, new Date());
			int result = sysMessageLogDAO.insert(message);

		}

		return ResultUtils.success();
	}

	/**
	 * 审批操作
	 *
	 * @param req
	 * @return
	 */
	@Override
	public ResponseBean ratifyJob(UpdateJobReq req) {

		Jobs jobs = jobsDAO.getJobs(req.getJobUuid());
		SysUser ratifyUser = sysUserDAO.getSysUser(jobs.getRatifyUser());
		try {
			JobsStateLog stateLog = jobsStateLogDAO.getJobsStateLogByStateAndUuid(Constants.JOBS_RATIFY_RESULT_WAIT,
					jobs.getUuid());
			jobsStateLogDAO.removeJobsStateLog(stateLog.getUuid());
		} catch (Exception e) {
			log.error("布控任务的待审批修改发生异常，异常信息：" + e.getMessage());
			e.printStackTrace();
		}
		if (jobs.getRatifyType() == 0) {
			// 布控
			if (req.getRatifyResult() == 1) {
				// 通过
				int newState = jobsTimerTask.getJobState(jobs);
				if (newState == Constants.JOB_STATE_RUNNING) {
					req.setState(newState);
				}
				try {
					jobsStateLogDAO.saveJobsStateLog(new JobsStateLog(null,
							(ratifyUser == null ? "" : ratifyUser.getUsername()) + "布控审批通过", jobs.getUuid(),
							Constants.JOBS_RATIFY_RESULT_YES, req.getRatifyMemo() == null ? "" : req.getRatifyMemo(),
							jobs.getCreateUser(), new Date()));
				} catch (Exception e) {
					log.error("布控任务的日志保存发生异常,异常信息:" + e.getMessage());
					e.printStackTrace();
				}
			} else if (req.getRatifyResult() == 2) {
				try {
					jobsStateLogDAO.saveJobsStateLog(new JobsStateLog(null,
							(ratifyUser == null ? "" : ratifyUser.getUsername()) + "布控审批不通过", jobs.getUuid(),
							Constants.JOBS_RATIFY_RESULT_NO, req.getRatifyMemo() == null ? "" : req.getRatifyMemo(),
							jobs.getCreateUser(), new Date()));
				} catch (Exception e) {
					log.error("布控任务的日志保存发生异常,异常信息:" + e.getMessage());
					e.printStackTrace();
				}

			}
		} else if (jobs.getRatifyType() == 1) {
			// 撤控
			if (req.getRatifyResult() == 1) {
				// 通过
				req.setState(Constants.JOB_STATE_HADTAKENCONTROL);
				try {
					jobsStateLogDAO.saveJobsStateLog(new JobsStateLog(null,
							(ratifyUser == null ? "" : ratifyUser.getUsername()) + "撤控审批通过", jobs.getUuid(),
							Constants.JOBS_RATIFY_RESULT_YES, req.getRatifyMemo() == null ? "" : req.getRatifyMemo(),
							jobs.getCreateUser(), new Date()));
				} catch (Exception e) {
					log.error("布控任务的日志保存发生异常,异常信息:" + e.getMessage());
					e.printStackTrace();
				}
			} else if (req.getRatifyResult() == 2) {
				try {
					jobsStateLogDAO.saveJobsStateLog(new JobsStateLog(null,
							(ratifyUser == null ? "" : ratifyUser.getUsername()) + "撤控审批不通过", jobs.getUuid(),
							Constants.JOBS_RATIFY_RESULT_NO, req.getRatifyMemo() == null ? "" : req.getRatifyMemo(),
							jobs.getCreateUser(), new Date()));
				} catch (Exception e) {
					log.error("布控任务的日志保存发生异常,异常信息:" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		int result = jobsDAO.ratifyJob(req);

		// 布控的申请用户默认订阅自己创建的布控报警
		SysSubscribe ss = new SysSubscribe();
		ss.setUuid(UuidUtil.getUuid());
		ss.setJobId(req.getJobUuid());
		ss.setUid(jobs.getCreateUser());
		List<SysSubscribe> subscribe = sysSubscribeDAO.queryByParam(jobs.getCreateUser(), req.getJobUuid(), null);
		if (CollectionUtils.isEmpty(subscribe)) {
			sysSubscribeDAO.saveSysSubscribe(ss);
		}
		// 消息
		int i = sysMessageLogDAO.insert(new SysMessageLog(req.getRatifyResult() == 1 ? 1 : 2, jobs.getUuid(),
				jobs.getCreateUser(), 0, 0, new Date()));

		return result > 0 ? ResultUtils.success() : ResultUtils.UNKONW_ERROR();

	}

	/**
	 * 布控：布控审批列表----不同布控状态的个数
	 *
	 * @param req
	 * @return
	 */
	@Override
	public ResponseBean jobListCount(JobListCountReq req) {

		List<JobListCountResp> list = new ArrayList<>();
		if (req.getIsRatifyList() != null && req.getIsRatifyList().intValue() == 1) {
			getListCount(list, 0, req, true);
			getListCount(list, 1, req, true);
			getListCount(list, 2, req, true);
		} else {
			getListCount(list, Constants.JOB_STATE_WAITSTART, req);
			getListCount(list, Constants.JOB_STATE_RUNNING, req);
			getListCount(list, Constants.JOB_STATE_PAUSE, req);
			getListCount(list, Constants.JOB_STATE_HADTAKENCONTROL, req);
			getListCount(list, Constants.JOB_STATE_INTAKENCONTROL, req);
			getListCount(list, Constants.JOB_STATE_DONE, req);
			getListCount(list, Constants.JOB_STATE_INREST, req);

		}
		return ResultUtils.success("resultList", list);
	}

	/**
	 * 审批或者布控列表
	 *
	 * @param list         返回的list集合
	 * @param state        布控或者审批状态
	 * @param req          请求的入参
	 * @param isRatifyList 是否是审批，默认是布控列表，可以不传
	 * @return
	 */
	private List<JobListCountResp> getListCount(List<JobListCountResp> list, Integer state, JobListCountReq req,
			boolean... isRatifyList) {
		Pager pager = new Pager();
		Map<String, String> map = new HashMap<>();
		map.put("querytype", req.getQuerytype() + "");
		if (isRatifyList != null && isRatifyList.length == 1 && isRatifyList[0] == true) {
			// 审批列表
			map.put("ratifyResult", state + "");
			map.put("uuid", req.getUserUuid() + "");
			pager.setF(map);
			list.add(new JobListCountResp(state, jobsDAO.ratifyListCount(pager)));
		} else {
			// 布控列表
			map.put("state", state + "");
			map.put("uuid", req.getUserUuid() + "");
			pager.setF(map);
			list.add(new JobListCountResp(state, jobsDAO.jobListCount(pager)));
		}

		return list;
	}

	@Override
	public ResponseBean info(UpdateJobReq req) {
		JobListResp resp = jobsDAO.info(req);
		// 如果是单目标库，返回的是车辆的uuid
		String obj_uuid = resp.getObj_uuid();
		if (StringUtils.isNotEmpty(obj_uuid)) {
			try {
				TemplateObjMotor templateObjMotor = templateObjMotorDAO.getTemplateObjMotor(obj_uuid);
				if (templateObjMotor != null) {
					String plateNo = templateObjMotor.getPlateNo().replace("_", "?").replaceAll("\\%", "*");
					resp.setPlateNo(plateNo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (req.getSource().intValue() == 1 || req.getSource().intValue() == 2) {
			// 详情页的来源 申请列表：审批通过详情1 ，审批不通过详情2
			SysMessageLog msgReq = new SysMessageLog();
			msgReq.setExtendUuId(resp.getUuid());
			msgReq.setType(req.getSource());
			msgReq.setUserUuid(resp.getCreateUser());
			int i = sysMessageLogDAO.setMsgReadByExtendUuId(msgReq);
		}
		if (req.getSource().intValue() == 3) {
			// 详情页的来源 审批列表：待审批详情3 将消息设置为已读
			SysMessageLog msgReq = new SysMessageLog();
			msgReq.setExtendUuId(resp.getUuid());
			msgReq.setType(3);
			msgReq.setUserUuid(resp.getRatifyUser());
			int i = sysMessageLogDAO.setMsgReadByExtendUuId(msgReq);
		}
		// 获取布控流程日志
		try {
			List<JobsStateLog> list = jobsStateLogDAO.getJobsStateLogs(req.getJobUuid());
			resp.setJobsStateLogs(list);
		} catch (Exception e) {
			log.error("布控任务获取布控流程日志发生异常，异常信息为：" + e.getMessage());
			e.printStackTrace();
		}

		return ResultUtils.success("model", resp);
	}

	@Override
	public ResponseBean getJobsUnReadMsgCount(String userUuid) {
		if ( StringUtils.isEmptyOrNull(userUuid) ) {
			return null;
		}
		List<JobListCountResp> list = new ArrayList<>();
		Map<String,Long> resultMap = sysMessageLogDAO.getUnReadCount(userUuid);
		list.add(new JobListCountResp(1,resultMap.get("p1").intValue()));
		list.add(new JobListCountResp(2,resultMap.get("p2").intValue()));
		list.add(new JobListCountResp(3,resultMap.get("p3").intValue()));
		return ResultUtils.success(list);
	}

	@Override
	public ResponseBean getJobsChannelByUuid(UpdateJobReq req) {
		List<JobListResp> jobsChannelByUuid = jobsDAO.getJobsChannelByUuid(req);
		return ResultUtils.success(jobsChannelByUuid);
	}


	@Override
	public int selectTemplatedbIdCountByObjUuid(String uuid) {
		int i = jobsDAO.selectTemplatedbIdCountByObjUuid(uuid);
		return i;
	}

	@Override
	public String getObjUuidByJobsUuid(String jobsUuid) {
		String objUuid = jobsDAO.getObjUuidByJobsUuid(jobsUuid);
		return objUuid;
	}

}