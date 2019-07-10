package com.sensing.core.utils.task;


import static com.sensing.core.utils.Constants.JOB_STATE_RUNNING;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.sensing.core.alarm.DataInitService;
import com.sensing.core.bean.Jobs;
import com.sensing.core.dao.IJobsDAO;
import com.sensing.core.utils.Constants;
import com.sensing.core.utils.StringUtils;
import com.sensing.core.utils.props.PropUtils;
import com.sensing.core.utils.time.DateUtil;

/**
 * 定时任务：布控模块
 */
@Component
public class JobsTimerTask {


    @Resource
    public IJobsDAO jobsDAO;

    @Resource
    public DataInitService dataInitService;
    private static final Log log = LogFactory.getLog(JobsTimerTask.class);

    public void startJobs() {

        log.info("~~~~~~ jobsTimer ~~~~~~" + DateUtil.DateToString(new Date()));

        List<Jobs> jobs = jobsDAO.getUpdateStateJob(Arrays.asList(Constants.JOB_STATE_WAITSTART, Constants.JOB_STATE_RUNNING, Constants.JOB_STATE_INREST));
        for (Jobs j : jobs) {
            int newState = getJobState(j);
            j.setNewState(newState);
        }
        /****  要开启的布控   *****/
        List<Jobs> runningList = jobs.stream().filter(j -> j.getState() != j.getNewState() && j.getNewState() == Constants.JOB_STATE_RUNNING).collect(Collectors.toList());

        /****  要  休息||已完成  的布控 ****/
        List<Jobs> stopList = jobs.stream().filter(j -> j.getState() != j.getNewState() && (j.getNewState() == Constants.JOB_STATE_INREST || j.getNewState() == Constants.JOB_STATE_DONE)).collect(Collectors.toList());

        //更新数据库里的状态
        runningList.addAll(stopList);
        if (!CollectionUtils.isEmpty(runningList)) {
            jobsDAO.updateStateBetch(runningList);
            dataInitService.init();
        }

    }


    public  int getJobState(Jobs job) {
        int nowState = job.getState().intValue();
        try {
            if (job.getState() == null || job.getRunWeek() == null) {
                return nowState;
            }
            if (StringUtils.isEmptyOrNull(job.getBeginTime()) || job.getBeginTime().split(":").length == 2) {
                return nowState;
            }
            /****  得到任务状态  ***/
            nowState = getJobsStateByDate(job, new Date(), 1);

            java.text.SimpleDateFormat timef = new java.text.SimpleDateFormat("HH:mm:ss");//设置日期格式
            Date startTime = timef.parse(job.getBeginTime());
            Date t1 = timef.parse("00:00:00");
            Date t2 = timef.parse("00:05:00");
            if (startTime.getTime() >= t1.getTime() && startTime.getTime() < t2.getTime()) {
                /****  当前时间推迟2（定时任务时间间隔）分钟得到任务状态（如果开启时间是00点到0点5分之间的任务）  ***/
                int nowStateAfter5 = getJobsStateByDate(job, DateUtil.addMinute(new Date(), PropUtils.getInt("job.update.time")), 2);
                //如果5分钟之后是运行状态，则提前将其打开，注意，这种情况下，开始时间就不用-5分钟（00:00:00 减之后会错）
                if (nowStateAfter5 == Constants.TASK_STAT_RUNNING.intValue()) {
                    nowState = nowStateAfter5;
                }
            }

        } catch (ParseException e) {
            log.error("JobsTimerTask，修改任务状态异常====" + StringUtils.getStackTrace(e));
        }

        return nowState;
    }

    /**
     * 根据时间判断Jobs的state值，注：该方法外部不可使用
     */
    private static int getJobsStateByDate(Jobs job, Date nowDate, int type) throws ParseException {
        int nowState = job.getState();
        java.text.SimpleDateFormat timef = new java.text.SimpleDateFormat("HH:mm:ss");//设置日期格式
        java.text.SimpleDateFormat dayf = new java.text.SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Date startTime = timef.parse(job.getBeginTime());
        Date endTime = timef.parse(StringUtils.isEmptyOrNull(job.getEndTime()) ? "23:59:59" : job.getEndTime());
        Date nowTime = timef.parse(timef.format(nowDate));
        /******************************************* 永久执行任务 开始日期startDate（有），开始时间startTime（有），结束日期endDate（无），结束时间endTime（有），运行周期（有） **************************************************************/
        if (job.getEndDate() == null) {
            //布控任务有开始时间 当前日期小于任务开始时间，未到执行时间
            long now = new Date().getTime();
            long start = DateUtil.StringToDate(dayf.format(job.getBeginDate()) + " " + "00:00:00").getTime();
            int distance = PropUtils.getInt("job.update.time");
            if (now < start && ((start - now) > (distance * 60 * 1000))) {
                return nowState;
            }

            int week = DateUtil.getWeek(nowDate);
            List<String> runWeeks = Arrays.asList(job.getRunWeek().split(","));

            if (runWeeks.contains(week + "")) {
                if (nowTime.getTime() >= DateUtil.addMinute(startTime, type == 1 ? -PropUtils.getInt("job.update.time") : 0).getTime() && nowTime.getTime() <= endTime.getTime()) {
                    //当前任务应该在进行中
                    nowState = JOB_STATE_RUNNING;
                } else if (nowTime.getTime() > endTime.getTime()) {
                    //改成休息中 或者 待启动
                    if (job.getState() == Constants.JOB_STATE_WAITSTART) {
                        nowState = Constants.JOB_STATE_WAITSTART;
                    } else if (job.getState() == JOB_STATE_RUNNING) {
                        nowState = Constants.JOB_STATE_INREST;
                    } else {
                        nowState = Constants.JOB_STATE_INREST;
                    }
                } else if (nowTime.getTime() < DateUtil.addMinute(startTime, type == 1 ? -PropUtils.getInt("job.update.time") : 0).getTime()) {
                    //未到任务开始时间
                    nowState = getNotStartState(job, nowState, type);
                }
            } else {
                nowState = getNotStartState(job, nowState, type);
            }
        } else if (job.getBeginDate() != null && job.getEndDate() != null) {
            /******************************************* 指定时间段任务 start  开始日期startDate（有），开始时间startTime（有），结束日期endDate（有），结束时间endTime（有），运行周期（有） **************************************************************/

            if ((dayf.parse(dayf.format(nowDate)).getTime()) < job.getBeginDate().getTime()) {
                //当前日期在任务开始日期之前
                nowState = Constants.JOB_STATE_WAITSTART;
            } else if (dayf.parse(dayf.format(nowDate)).getTime() >= job.getBeginDate().getTime() && dayf.parse(dayf.format(nowDate)).getTime() <= job.getEndDate().getTime()) {
                //当前日期在任务开始日期之间
                int week = DateUtil.getWeek(nowDate);
                List<String> runWeeks = Arrays.asList(job.getRunWeek().split(","));

                if (runWeeks.contains(week + "")) {
                    if (nowTime.getTime() < DateUtil.addMinute(startTime, type == 1 ? -PropUtils.getInt("job.update.time") : 0).getTime()) {
                        nowState = getNotStartState(job, nowState, type);
                    } else if (nowTime.getTime() >= DateUtil.addMinute(startTime, type == 1 ? -PropUtils.getInt("job.update.time") : 0).getTime() && nowTime.getTime() <= endTime.getTime()) {
                        //当前任务应该在进行中
                        nowState = JOB_STATE_RUNNING;
                    } else if (nowTime.getTime() > endTime.getTime()) {
                        //改成休息中  待启动  已完成
                        if (job.getState() == Constants.JOB_STATE_WAITSTART) {
                            nowState = Constants.JOB_STATE_WAITSTART;
                        } else if (job.getState() == JOB_STATE_RUNNING) {
                            //之前是：运行中，要修改成休息中或者已完成,取决于下次任务是否能执行
                            List<String> runWeeks2 = new ArrayList<>();
                            runWeeks2.addAll(runWeeks);
                            runWeeks2.addAll(runWeeks);
                            int i = runWeeks.indexOf(week + "") + 1;
                            int nextWeek = Integer.valueOf(runWeeks2.get(i)).intValue();
                            //下次执行的日期
                            Date nextRunTime = null;
                            if (nextWeek > week) {
                                nextRunTime = DateUtil.addDay(dayf.parse(dayf.format(nowDate)), nextWeek - week);
                            } else if (nextWeek < week) {
                                nextRunTime = DateUtil.addDay(dayf.parse(dayf.format(nowDate)), week - nextWeek);
                            } else if (nextWeek == week) {
                                nextRunTime = DateUtil.addDay(dayf.parse(dayf.format(nowDate)), week);
                            }
                            if (dayf.parse(dayf.format(nextRunTime)).getTime() > job.getEndDate().getTime()) {
                                nowState = Constants.JOB_STATE_DONE;
                            } else {
                                // 休息中
                                nowState = Constants.JOB_STATE_INREST;
                            }
                        } else if (job.getState() == Constants.JOB_STATE_INREST) {
                            nowState = Constants.JOB_STATE_INREST;
                        } else {
                            nowState = Constants.JOB_STATE_INREST;
                        }
                    }
                } else {
                    nowState = getNotStartState(job, nowState, type);
                }
            } else if ((dayf.parse(dayf.format(nowDate)).getTime()) > job.getEndDate().getTime()) {
                nowState = Constants.JOB_STATE_DONE;
            }
            /******************************************* 指定时间段任务 end **************************************************************/
        }

        return nowState;
    }


    /**
     * @param job
     * @param nowState
     * @return
     */
    private static int getNotStartState(Jobs job, int nowState, int type) throws ParseException {
        //当前任务是休息中
        if (job.getState() == Constants.JOB_STATE_WAITSTART) {
            nowState = Constants.JOB_STATE_WAITSTART;
        } else if (job.getState() == Constants.JOB_STATE_RUNNING) {
            nowState = Constants.JOB_STATE_INREST;
        } else if (job.getState() == Constants.JOB_STATE_INREST) {
            nowState = Constants.JOB_STATE_INREST;
        } else if (job.getState() == Constants.JOB_STATE_PAUSE) {
            nowState = Constants.JOB_STATE_INREST;
        } else if (job.getState() == Constants.TASK_STAT_FAILEE) {
            //之前是未启动过，当前是待启动状态，之前启动过，当前是休息中
            nowState = Constants.JOB_STATE_INREST;
        }
        return nowState;
    }

    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//        list.add("3333");
//        List<String> list2 = list.stream().filter(a -> "e".equals(a)).collect(Collectors.toList());
//
//        list.addAll(list2);

//        try {
//            java.text.SimpleDateFormat dayAndTimef = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            java.text.SimpleDateFormat dayf = new java.text.SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//
//            //指定日期 开始日期startDate（有），开始时间startTime（有），结束日期endDate（有），结束时间endTime（有），运行周期（有）
//            Jobs jobs = new Jobs();
//            jobs.setState(Constants.JOB_STATE_RUNNING);
//
//            jobs.setBeginDate(dayf.parse("2018-12-01"));
//            jobs.setEndDate(dayf.parse("2018-12-29"));
//
//            jobs.setBeginTime("00:00:00");
//            jobs.setEndTime("12:00:00");
//            jobs.setRunWeek("1,3,4,5");
//
//            int jobState = getJobsStateByDate(jobs, dayAndTimef.parse("2018-12-27 15:01:00"), 1);
////            int jobState = getJobsStateByDate(jobs,DateUtil.addMinute(dayAndTimef.parse("2018-12-27 00:00:00"),5),2);
//
//            System.out.println(Constants.JOB_STATE_MAP.get(jobState));
//
//            //永久任务 开始日期startDate（有），开始时间startTime（有），结束日期endDate（无），结束时间endTime（有），运行周期（有）
////            Jobs jobs = new Jobs();
////            jobs.setState(Constants.JOB_STATE_RUNNING);
////            Date parse = dayf.parse("2018-09-02");
////            jobs.setBeginDate(parse);
////            jobs.setBeginTime("00:00:00");
////            jobs.setEndTime("12:00:00");
////            jobs.setRunWeek("1,2,3,4,5,6,7");
////
////            int jobState = getJobsStateByDate(jobs,dayAndTimef.parse("2018-09-02 12:01:00"),1);
//
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

    }


}
