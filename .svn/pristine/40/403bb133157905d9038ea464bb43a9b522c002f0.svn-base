package com.sensing.core.utils.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Joiner;
import com.sensing.core.bean.Channel;
import com.sensing.core.bean.RpcLog;
import com.sensing.core.bean.Task;
import com.sensing.core.bean.TaskChannelResp;
import com.sensing.core.dao.IChannelDAO;
import com.sensing.core.dao.IRpcLogDAO;
import com.sensing.core.dao.ITaskChannelDAO;
import com.sensing.core.dao.ITaskDAO;
import com.sensing.core.service.impl.CaptureThriftServiceImpl;
import com.sensing.core.service.impl.ChannelServiceImpl;
import com.sensing.core.thrift.cap.bean.CapReturn;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.Pager;
import com.sensing.core.utils.StringUtils;
import com.sensing.core.utils.WebUtil;
import com.sensing.core.utils.props.PropUtils;
import com.sensing.core.utils.time.DateUtil;

/**
 * 定时任务：实时结构化任务
 */
@Component
public class TaskTimerTask {

    private static final Log log = LogFactory.getLog(TaskTimerTask.class);


    @Resource
    public ITaskDAO taskDAO;
    @Resource
    public ITaskChannelDAO taskChannelDAO;
    @Resource
    public IChannelDAO channelDAO;
    @Resource
    public ChannelServiceImpl channelServiceImpl;
    @Resource
    public CaptureThriftServiceImpl captureThriftServiceImpl;
    @Resource
    public IRpcLogDAO rpcLogDAO;
    //是否在处理中
    private static volatile boolean isProcess = false;

    /**
     * 修改task状态的定时任务
     */
    public void startTask() {
        try {
            if (isProcess) {
                log.info("~~~~~~ taskTimer ~~~~~~isProcess为" + isProcess + "~~当前有任务正在执行中");
                return;
            }
            isProcess = true;
            log.info("~~~~~~ taskTimer ~~~~~~" + DateUtil.DateToString(new Date()));

            //  查询当前时间段有效的任务(type in (1)  is_del=0  state in（待启动，处理中，休息中,失败) )
            List<Task> tasks = taskDAO.getUpdateStateTask(Arrays.asList(Constants.TASK_STAT_WAITSTART,Constants.TASK_STAT_RUNNING, Constants.TASK_STAT_INREST, Constants.TASK_STAT_FAILEE));
            if (CollectionUtils.isEmpty(tasks)) {
                return;
            }
            for (Task t : tasks) {
                /*** 得到任务状态 ***/
                int newState = getTaskState(t);
                t.setNewState(newState);
            }

            /***********************  关联通道为空的情况，置成已完成的情况 start **********************/
            List<String> taskUuidList = tasks.stream().map(a -> a.getUuid()).collect(Collectors.toList());
            List<TaskChannelResp> taskChannel = taskChannelDAO.getTaskChannelByTaskIds(taskUuidList);
            List<Task> emptyTaskList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(taskChannel)) {
                for (String taskuuId : taskUuidList) {
                    List<TaskChannelResp> nowTaskChannel = taskChannel.stream().filter(t -> t.getTaskUuid().equals(taskuuId)).collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(nowTaskChannel)) {
                        // 任务存在，任务关联的通道是不存在的，这种情况下，应该将该通道设置成已完成状态（产生原因：1、删通道以后，对应的任务没有其他关联的通道；2、录入的时候taskchannel里的cnanneluuid录入错误）
                        List<Task> nowTask = tasks.stream().filter(a -> a.getUuid().equals(taskuuId)).collect(Collectors.toList());
                        nowTask.stream().forEach(a -> a.setNewState(Constants.TASK_STAT_DONE));
                        emptyTaskList.addAll(nowTask);
                    }
                }
                if (!CollectionUtils.isEmpty(emptyTaskList)) {
                    int i = taskDAO.setUpdateStateTasks(emptyTaskList);
                    if (i > 0) {
                        setTaskRpcLog(emptyTaskList, taskChannel);
                    }
                    tasks.removeAll(emptyTaskList);
                }
            }
            /***********************  关联通道为空的情况，置成已完成的情况 end **********************/

            List<Task> updateTasks = tasks.stream().filter(t -> t.getState() != t.getNewState()).collect(Collectors.toList());

            /**************************************************  要开启的任务   *************************************************/
            List<Task> openTaskList = updateTasks.stream().filter(a -> a.getNewState() == Constants.TASK_STAT_RUNNING).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(openTaskList)) {
                openTask(openTaskList, taskChannel);
            }

            /**************************************************  要  休息||已完成  的任务    *************************************************/
            List<Task> doneAndInRestList = updateTasks.stream().filter(a -> a.getNewState() == Constants.TASK_STAT_DONE || a.getNewState() == Constants.TASK_STAT_INREST).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(doneAndInRestList)) {
                takeRestAndDoneTask(doneAndInRestList, taskChannel);
            }

        } catch (Exception e) {
            log.error(e.getMessage() + StringUtils.getStackTrace(e));
            try {
                setRPcLog(new RpcLog("定时任务", "失败", StringUtils.getStackTrace(e), 3));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            isProcess = false;
        }


    }


    /**
     * 要  休息||已完成  的任务
     *
     * @param
     * @param taskChannel
     * @return 1:修改通道状态正常   0：openclosechannel不报错，返回结果是失败  -1: openclosechannel报错异常
     */
    public int takeRestAndDoneTask(List<Task> taskList, List<TaskChannelResp> allTaskChannel) {

        List<String> taskUuIds = taskList.stream().map(t -> t.getUuid()).collect(Collectors.toList());
        List<TaskChannelResp> nowTaskChannel = allTaskChannel.stream().filter(a -> taskUuIds.contains(a.getTaskUuid())).collect(Collectors.toList());
        List<String> runningChannelIds = getRunTaskChannelIds(taskList.stream().map(a -> a.getUuid()).collect(Collectors.toList()), 1);

        //2018/8/28 lxh 正在运行的通道，查看是cap_state是否为1，不为1，则打开
        List<TaskChannelResp> tcList = new ArrayList<>();
        for (TaskChannelResp tc : nowTaskChannel) {
            // 查看通道是否被其他任务引用，当前正在使用中的通道不能关闭
            if ((runningChannelIds == null) || (runningChannelIds != null && !runningChannelIds.contains(tc.getChannelUuid()))) {
                tcList.add(tc);
            }
        }
        if (!CollectionUtils.isEmpty(tcList)) {
            return updateChannel(tcList, taskList, Constants.CAP_STAT_CLOSE);
        } else {
            //为空就不用通知抓拍,直接更新任务状态即可
            taskDAO.setUpdateStateTasks(taskList);
            setTaskRpcLog(taskList, tcList);
            return 1;
        }
    }

    /**
     * 要开启的任务
     *
     * @param
     * @param
     * @return 1:修改通道状态正常   0：openclosechannel不报错，返回结果是失败  -1: openclosechannel报错异常
     */
    public int openTask(List<Task> taskList, List<TaskChannelResp> allTaskChannel) {
        List<String> taskUuIds = taskList.stream().map(t -> t.getUuid()).collect(Collectors.toList());
        List<TaskChannelResp> nowTaskChannel = allTaskChannel.stream().filter(a -> taskUuIds.contains(a.getTaskUuid())).collect(Collectors.toList());

        List<TaskChannelResp> tcList = new ArrayList<>();
        for (TaskChannelResp tc : nowTaskChannel) {
            if (tc.getCap_stat() == null || tc.getCap_stat().intValue() == Constants.CAP_STAT_CLOSE.intValue()) {
                tcList.add(tc);
            }
        }
        if (!CollectionUtils.isEmpty(tcList)) {
            return updateChannel(tcList, taskList, Constants.CAP_STAT_OPEN);
        } else {
            //为空就不用通知抓拍,直接更新任务状态即可
            taskDAO.setUpdateStateTasks(taskList);
            setTaskRpcLog(taskList, tcList);
            return 1;
        }
    }

    /**
     *
     * @param tc          通道
     * @param t           当前的任务
     * @param newCapState 1：开启通道  0：关闭通道
     * @return  1:修改通道状态正常   0：openclosechannel不报错，返回结果是失败  -1: openclosechannel报错异常
     */
    private int updateChannel(List<TaskChannelResp> tc, List<Task> tasks, int newCapState) {
        int result = 1;
        String errorMsg = "";
        CapReturn capReturn = null;
        List<String> channelIds = tc.stream().map(c -> c.getChannelUuid()).collect(Collectors.toList());
        try {
            /*** 更新抓拍 ***/
            capReturn = captureThriftServiceImpl.OpenCloseChannels(channelIds, newCapState, 0);
            if (capReturn.res < 0) {
                result = 0;
                errorMsg = capReturn.getMsg().substring(0, 1000);
                log.error("定时任务通道状态更新异常1===" + errorMsg);
            } else {
                result = 1;
            }
        } catch (Exception e) {
            errorMsg = StringUtils.getStackTrace(e).substring(0, 1000);
            log.error("定时任务通道状态更新异常2===" + errorMsg);
            result = -1;
        }
        if (result == 1) {
            Channel c = new Channel();
            c.setModifyTime(new Date().getTime() / 1000);
            c.setCapStat(newCapState);
            for (TaskChannelResp t : tc) {
                c.setUuid(t.getChannelUuid());
                channelDAO.updateChannelCap(c);
            }
        } else {
            /***  通知抓拍异常 ****/
            tasks.stream().forEach(t -> t.setNewState(Constants.TASK_STAT_FAILEE));
        }
        taskDAO.setUpdateStateTasks(tasks);
        setTaskRpcLog(tasks, tc, errorMsg);
        return result;
    }

    /**
     * 得到正在运行中的任务
     *
     * @param taskUuid 任务的uuid
     * @param type     1：移除任务本身 0：不移除任务本身   (正在处理中的任务置成已完成状态，要排除掉自身)
     * @return
     */
    public List<String> getRunTaskChannelIds(List<String> taskUuid, int type) {
        // 查询当前正在被使用的通道
        Pager pager = new Pager();
        Map<String, String> map = new HashMap<>();
        map.put("state",Constants.TASK_STAT_RUNNING + "");
        map.put("type", 1 + "");
        pager.setF(map);
        pager.setPageFlag(null);
        List<Task> runningTasks = taskDAO.queryList(pager);
        //移除掉自己任务本身
        if (type == 1) {
            runningTasks.removeIf(a -> taskUuid.contains(a.getUuid()));
        }
        List<String> runningChannelIds = null;
        if (!CollectionUtils.isEmpty(runningTasks)) {
            List<TaskChannelResp> runningChannel = taskChannelDAO.getTaskChannelByTaskIds(runningTasks.stream().map(a -> a.getUuid()).collect(Collectors.toList()));
            runningChannelIds = runningChannel.stream().map(a -> a.getChannelUuid()).collect(Collectors.toList());
        }
        return runningChannelIds;
    }

    /****
     * 设置任务状态值
     */
    public int getTaskState(Task task) {
        int nowState = task.getState().intValue();
        try {
            if (task.getState() == null || task.getType() == null || task.getRunWeek() == null) {
                return nowState;
            }
            if (StringUtils.isEmptyOrNull(task.getAnalyStartTime()) || task.getAnalyStartTime().split(":").length == 2) {
                return nowState;
            }
            
            /****  得到任务状态  ***/
            nowState = getTaskStateByDate(task, new Date(), 1);

            java.text.SimpleDateFormat timef = new java.text.SimpleDateFormat("HH:mm:ss");//设置日期格式
            
            Date startTime = timef.parse(task.getAnalyStartTime());
//            Date endTime = timef.parse(task.getAnalyEndTime());
            Date t1 = timef.parse("00:00:00");
            Date t2 = timef.parse("00:05:00");
            if (startTime.getTime() >= t1.getTime() && startTime.getTime() < t2.getTime()) {
                /****  当前时间推迟2（定时任务时间间隔）分钟得到任务状态（如果开启时间是00点到0点5分之间的任务）  ***/
                int nowStateAfter5 = getTaskStateByDate(task, DateUtil.addMinute(new Date(), PropUtils.getInt("task.update.time")), 2);
                //如果5分钟之后是运行状态，则提前将其打开，注意，这种情况下，开始时间就不用-5分钟（00:00:00 减之后会错）
                if (nowStateAfter5 == Constants.TASK_STAT_RUNNING.intValue()) {
                    nowState = nowStateAfter5;
                }
            }

        } catch (ParseException e) {
            log.error("TaskTimerTask，修改任务状态异常====" + StringUtils.getStackTrace(e));
        }

        return nowState;
    }

    /**
     * 根据时间判断task的state值，注：该方法外部不可使用
     * @param task 任务
     * @param nowDate  时间
     * @param type 
     * @return 任务在该时间点下的状态
     * @throws ParseException
     * @author mingxingyu
     * @date   2019年5月27日 下午2:00:05
     */
    private  int getTaskStateByDate(Task task, Date nowDate, int type) throws ParseException {
        int nowState = task.getState();
        java.text.SimpleDateFormat timef = new java.text.SimpleDateFormat("HH:mm:ss");//设置日期格式
        java.text.SimpleDateFormat dayf = new java.text.SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//        java.text.SimpleDateFormat sdfH = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date startTime = timef.parse(task.getAnalyStartTime());
        Date endTime = timef.parse(task.getAnalyEndTime());
        Date nowTime = timef.parse(timef.format(nowDate));
        /******************************************* 永久执行任务 开始日期begindDate（无），开始时间startTime（无），结束日期endDate（无），结束时间endTime（无），运行周期（有）**************************************************************/
        if (task.getBeginDate() == null && task.getEndDate() == null) {
        	//运行的周数判断
            int week = DateUtil.getWeek(nowDate);
            List<String> runWeeks = Arrays.asList(task.getRunWeek().split(","));
            if (runWeeks.contains(week + "")) {
            	//当前时间在任务的时间范围中
            	//当前时间大于开始时间-update.time,当期时间小于结束时间
                if (nowTime.getTime() >= DateUtil.addMinute(startTime, type == 1 ? -PropUtils.getInt("task.update.time") : 0).getTime() && nowTime.getTime() <= endTime.getTime()) {
                    nowState = Constants.TASK_STAT_RUNNING;
                } 
                //当前时间在任务的范围时间后
                else if (nowTime.getTime() > endTime.getTime()) {
                    //休息中
//                	if (task.getState() == Constants.TASK_STAT_WAITSTART) {
//                        nowState = Constants.TASK_STAT_WAITSTART;
//                    } else if (task.getState() == TASK_STAT_RUNNING) {
//                        nowState = Constants.TASK_STAT_INREST;
//                    } else {
//                        nowState = Constants.TASK_STAT_INREST;
//                    }
                    nowState = Constants.TASK_STAT_INREST;
                } 
                //当前的时间在任务的开始时间之前
                else if (nowTime.getTime() < DateUtil.addMinute(startTime, type == 1 ? -PropUtils.getInt("task.update.time") : 0).getTime()) {
                    //未到任务开始时间
                	//nowState = getNotStartState(task, nowState, type);
                	
                	//当前时间和任务的创建日期是同一天，待启动；否则，休息中
                	String taskDay = dayf.format(task.getCreateTime());
                	String nowDay = dayf.format(nowDate);
                	if ( taskDay.equals(nowDay) ) {
                		nowState = Constants.TASK_STAT_WAITSTART;
					}else{
						nowState = Constants.TASK_STAT_INREST;
					}
                }
            } else {
            	//当天的周期不在任务的运行的周期范围内
            	//nowState = getNotStartState(task, nowState, type);
                
            	//当前时间和任务的创建日期是同一天，待启动；否则，休息中
                String taskDay = dayf.format(task.getCreateTime());
                String nowDay = dayf.format(nowDate);
                if ( taskDay.equals(nowDay) ) {
                	nowState = Constants.TASK_STAT_WAITSTART;
                }else{
                	nowState = Constants.TASK_STAT_INREST;
                }
                
            }
        } else if (task.getBeginDate() != null && task.getEndDate() != null) {
        	int week = DateUtil.getWeek(nowDate);
        	List<String> runWeeks = Arrays.asList(task.getRunWeek().split(","));
        	
            /******************************************* 指定时间段任务 start 开始日期begindDate（有），开始时间startTime（有），结束日期endDate（有），结束时间endTime（有），运行周期（有） **************************************************************/
            if ((dayf.parse(dayf.format(nowDate)).getTime()) < task.getBeginDate().getTime()) {
                //当前日期在任务开始日期之前
                //nowState = Constants.TASK_STAT_WAITSTART;
                //当前周数不在任务的运行周数内，这个状态可能是  待启动，休息中，已完成
            	nowState = getNoWorkStateByWeek(nowDate,runWeeks,dayf.format(task.getBeginDate()),task.getAnalyStartTime(),
            			dayf.format(task.getEndDate()),task.getAnalyEndTime()); 
            } else if (dayf.parse(dayf.format(nowDate)).getTime() >= task.getBeginDate().getTime() && dayf.parse(dayf.format(nowDate)).getTime() <= task.getEndDate().getTime()) {
                //当前日期在任务开始日期之间

                if (runWeeks.contains(week + "")) {
                	//当前时间 < 任务开始运行时间
                    if (nowTime.getTime() < DateUtil.addMinute(startTime, type == 1 ? -PropUtils.getInt("task.update.time") : 0).getTime()) {
                    	//当前时间和任务的创建日期是同一天，待启动；否则，休息中
                    	String taskDay = dayf.format(task.getBeginDate());
                    	String nowDay = dayf.format(nowDate);
                    	if ( taskDay.equals(nowDay) ) {
                    		nowState = Constants.TASK_STAT_WAITSTART;
    					}else{
    						nowState = Constants.TASK_STAT_INREST;
    					}
                    	
                    } else if (nowTime.getTime() >= DateUtil.addMinute(startTime, type == 1 ? -PropUtils.getInt("task.update.time") : 0).getTime() && nowTime.getTime() <= endTime.getTime()) {
                        //当前任务应该在进行中
                        nowState = Constants.TASK_STAT_RUNNING;
                    } else if (nowTime.getTime() > endTime.getTime()) {
                        //改成休息中  已完成
                        if (task.getState() == Constants.TASK_STAT_WAITSTART) {
                        	//nowState = Constants.TASK_STAT_WAITSTART;
                        	nowState = Constants.TASK_STAT_INREST;
                        } else if (task.getState() == Constants.TASK_STAT_RUNNING || task.getState() == Constants.TASK_STAT_INREST ) {
                            //之前是：运行中，要修改成休息中或者已完成,取决于下次任务是否能执行
//                            List<String> runWeeks2 = new ArrayList<>();
//                            runWeeks2.addAll(runWeeks);
//                            runWeeks2.addAll(runWeeks);
//                            int i = runWeeks.indexOf(week + "") + 1;
//                            int nextWeek = Integer.valueOf(runWeeks2.get(i)).intValue();
//                            //下次执行的日期
//                            Date nextRunTime = null;
//                            if (nextWeek > week) {
//                                nextRunTime = DateUtil.addDay(dayf.parse(dayf.format(nowDate)), nextWeek - week);
//                            } else if (nextWeek < week) {
//                                nextRunTime = DateUtil.addDay(dayf.parse(dayf.format(nowDate)), week - nextWeek);
//                            } else if (nextWeek == week) {
//                                nextRunTime = DateUtil.addDay(dayf.parse(dayf.format(nowDate)), week);
//                            }
//                            if (dayf.parse(dayf.format(nextRunTime)).getTime() >= task.getEndDate().getTime()) {
//                                nowState = Constants.TASK_STAT_DONE;
//                            } else {
//                                // 休息中
//                                nowState = Constants.TASK_STAT_INREST;
//                            }
                        	nowState = getNoWorkStateByWeek(nowDate,runWeeks,dayf.format(task.getBeginDate()),task.getAnalyStartTime(),
                        			dayf.format(task.getEndDate()),task.getAnalyEndTime()); 
//                        } else if (task.getState() == Constants.TASK_STAT_INREST) {
//                        	//当前是休息中，下个状态可能是休息中、已完成
//                        	
//                        	//判断当前的日期和任务的结束日期是否相同，相同的话，任务就是结束，不相同的话，就是休息中
//                        	if ( dayf.format(nowDate).equals(dayf.format(task.getEndDate())) ) {
//                        		nowState = Constants.TASK_STAT_DONE;
//							}else{
//								nowState = Constants.TASK_STAT_INREST;
//							}
                        } else {
                            nowState = Constants.TASK_STAT_INREST;
                        }
                    }
                } else {
                	//当前周数不在任务的运行周数内，这个状态可能是  待启动，休息中，已完成
                	nowState = getNoWorkStateByWeek(nowDate,runWeeks,dayf.format(task.getBeginDate()),task.getAnalyStartTime(),
                			dayf.format(task.getEndDate()),task.getAnalyEndTime()); 
                }
            } else if ((dayf.parse(dayf.format(nowDate)).getTime()) > task.getEndDate().getTime()) {
                nowState = Constants.TASK_STAT_DONE;
            }
            /******************************************* 指定时间段任务 end **************************************************************/
        }

        return nowState;
    }
    
    
    /**
     * 根据运行的周数判断任务的状态
     * @param date	当前时间[Date对象]
     * @param runWeekList	运行的周数[1,2,3,4,5]
     * @param endDate	结束的日期[2019-10-10]
     * @param endTime	结束的时间[10:00:00]
     * @return	 状态[待启动、休息中、已完成]
     * @author mingxingyu
     * @throws ParseException 
     * @throws Exception 
     * @date   2019年6月26日 下午4:00:44
     */
    private int getNoWorkStateByWeek(Date date,List<String> runWeekList,String startDate,String startTime,String endDate,String endTime) throws ParseException {
    	
    	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	//当前日期是周几，获取到下一个任务需要执行的周几数
		int weekDay = DateUtil.getWeek(date);
		//下个周几是任务执行
		int nextWeekDay = 0;
		//是否是下周执行
		int nextWeekStatus = 0;
    			
		//找到本周的下一天，或者是第二周的第一天
		for (int i = 0; i < runWeekList.size() ; i++) {
			if ( runWeekList.get(i).equals(weekDay) ) {
				if ( i == runWeekList.size() - 1 ) {
					nextWeekDay = Integer.parseInt(runWeekList.get(0));
					nextWeekStatus = 1;
				}else{
					nextWeekDay = i+1;
				}
				break;
			}
			
			if ( Integer.parseInt(runWeekList.get(i)) > weekDay ) {
				nextWeekDay = Integer.parseInt(runWeekList.get(i));
				break;
			}
				
		}

		//不存在，就是下一周的第一天
		if ( nextWeekDay == 0 ) {
			nextWeekDay = Integer.parseInt(runWeekList.get(0));
			nextWeekStatus = 1;
		}
		//根据周几获取到下一天执行的日期
		Date dateByWeek = DateUtil.getDateByWeek(date,nextWeekDay,nextWeekStatus);
    			
		Date end = sdf2.parse(endDate+" "+endTime);
    			
		//判断下一天执行的日期是否是大于结束日期
		//是：已完成；不是：休息中|待启动
		if ( end.getTime() > sdf1.parse(sdf1.format(dateByWeek)).getTime() ) {
			if ( date.getTime() < sdf2.parse(startDate+" "+startTime).getTime() ) {
				return Constants.TASK_STAT_WAITSTART;
			}else{
				return Constants.TASK_STAT_INREST;
			}
		}else{
			return Constants.TASK_STAT_DONE;
		}
		
    }


    /**
     * 记录日志：修改打开关闭通道
     *
     * @Author: LXH
     * @Date: 2018/9/14
     * 修改任务类型
     */
    public void setChannelRpcLog(List<TaskChannelResp> tcs, List<Task> task, int preState, CapReturn capReturn, String errorMsg) {
        RpcLog rl = new RpcLog();
        if (capReturn == null) {
            rl.setResult("失败");
            rl.setType(Constants.RPC_LOG_TYPE_RUNNING);
            rl.setErrorMsg(errorMsg);
        } else {
            rl.setResult(capReturn.res >= 0 ? "成功" : "失败");
            //type=3,运行日志
            if (capReturn.res < 0) {
                rl.setType(Constants.RPC_LOG_TYPE_RUNNING);
                rl.setErrorMsg(capReturn.msg);
            } else {
                rl.setType(Constants.RPC_LOG_TYPE_OPERATE);
            }
        }
        rl.setName("OpenCloseChannels");
        rl.setRpcType("thrift");
        rl.setTodo(preState == 0 ? "打开通道" : "关闭通道");
        //rl.setName(c.getUuid());

        List<String> nameAndUuidList = tcs.stream().map(a -> a.getChannel_name() + "==" + a.getChannelUuid() + "==").collect(Collectors.toList());
        rl.setMemo("==channelUuid==" + Joiner.on(",").join(nameAndUuidList) + "==之前状态==" + preState + "==修改之后的状态值==" + ((preState == 0) ? 1 + "" : 0 + "") + "==关联task==" + task.stream().map(t -> t.getUuid()).collect(Collectors.toList()) + task.stream().map(t -> t.getName()).collect(Collectors.toList()));
        setRPcLog(rl);
    }

    /**
     * 记录日志：修改任务状态值
     *
     * @Author: LXH
     * @Date: 2018/9/14
     */
    public void setTaskRpcLog(List<Task> taskList, List<TaskChannelResp> taskChannel, String... errorMsg) {
        if (CollectionUtils.isEmpty(taskList)) {
            return;
        }
        RpcLog rl = new RpcLog();
        rl.setResult("成功");
        rl.setRpcType("spring-task");
        rl.setTodo("修改任务状态值");
        rl.setType(Constants.RPC_LOG_TYPE_OPERATE);
        StringBuilder builder = new StringBuilder();
        for (Task t : taskList) {
            builder.append(" task名称：" + t.getName());
            builder.append(" uuid：" + t.getUuid() + " ");
            builder.append(" 之前状态值：" + Constants.TASK_STAT_MAP.get(t.getState()) + " ");
            builder.append(" 之后状态值：" + Constants.TASK_STAT_MAP.get(t.getNewState()) + " ");
            if (!CollectionUtils.isEmpty(taskChannel)) {
                List<TaskChannelResp> channels = taskChannel.stream().filter(a -> a.getTaskUuid().equals(t.getUuid())).collect(Collectors.toList());
                builder.append(" 关联的通道状态修改：" + channels.stream().map(c -> c.getChannelUuid()).collect(Collectors.toList()) + " " + channels.stream().map(c -> c.getChannel_name()).collect(Collectors.toList()) + " ");
            } else {
                builder.append("关联通道不需要修改状态");
            }
        }
        if (errorMsg != null && errorMsg.length == 1) {
            builder.append(errorMsg.toString());
        }

        rl.setMemo(new String(builder));
        setRPcLog(rl);
    }

    public void setRPcLog(RpcLog rl) {
        rl.setCallTime(new Date());
        rl.setMode(Constants.INTERFACE_CALL_TYPE_INIT);
        rl.setModule(Constants.SEVICE_MODEL_TASK);
        rl.setIp(WebUtil.getLocalIP());
        rpcLogDAO.saveRpcLog(rl);
    }


    /*
    public static void main(String[] args) {
        try {

            java.text.SimpleDateFormat dayAndTimef = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.text.SimpleDateFormat dayf = new java.text.SimpleDateFormat("yyyy-MM-dd");//设置日期格式

            //指定日期 开始日期startDate（有），开始时间startTime（有），结束日期endDate（有），结束时间endTime（有），运行周期（有）
            Task jobs = new Task();
            jobs.setState(Constants.TASK_STAT_WAITSTART);

            jobs.setBeginDate(dayf.parse("2018-12-01"));
            jobs.setEndDate(dayf.parse("2018-12-29"));

            jobs.setStartTime("08:00:00");
            jobs.setEndTime("12:00:00");

            jobs.setAnalyStartTime("00:00:00");
            jobs.setAnalyEndTime("22:00:00");

            jobs.setRunWeek("1,3,4,5,6");

            int jobState = 0;

            jobState = getTaskStateByDate(jobs, dayAndTimef.parse("2018-11-30 23:59:00"), 1);

//             jobState = getTaskStateByDate(jobs,DateUtil.addMinute(dayAndTimef.parse("2018-11-30 23:59:00"),5),2);

//            System.out.println(Constants.TASK_STAT_MAP.get(jobState));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
     */

}

