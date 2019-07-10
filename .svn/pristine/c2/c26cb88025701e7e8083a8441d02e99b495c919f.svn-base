package com.sensing.core.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.sensing.core.utils.Constants;
import com.sensing.core.utils.StringUtils;
import org.apache.http.util.TextUtils;

/**
 * @author wenbo
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private String uuid;//任务uuid
    private String name;//任务名称
    private Integer type; //1:实时分析的任务   2：历史分析的任务  3：离线视频任务
    private Integer state;//任务状态 0:待启动(任务从未进行) 1:处理中  2:休息中 3:已暂停 4:已停止 5:已完成 6:失败
    private java.util.Date beginDate;//如果是永久任务则beginDate和endDate是为空的，都有值则是按时间段执行的任务
    private String beginDateStr;
    private java.util.Date endDate;
    private String endDateStr;
    private String startTime;
    private String endTime;
    private String runWeek; //  2018/8/27 lxh 如果是按周期执行的任务，该字段不为空（1,2,3）,
    private String analyStartTime;//开始分析时间
    private String analyEndTime;//结束分析时间
    private Date analyBeginDate;//开始分析日期
    private Date analyEndDate;//开始分析日期
    private String analyBeginDateStr;
    private String analyEndDateStr;
    private String analyType;
    private String analyTypeTag;
    private Integer autoStart;
    private Integer isDeleted;
    private String createUser;
    private java.util.Date createTime;//
    private String createTimeStr;
    private String modifyUser;
    private java.util.Date modifyTime;
    private String modifyTimeStr;
    private String memo;
    private String stateTag;
    private String typeTag;
    private Integer videoSpeed;//视频倍速
    private Integer newState;//新状态
    private String createUserUuid;
    private Integer isPreState;// 为1 将改任务设置为停止


    public Integer getIsPreState() {
        return isPreState == null ? 0 : isPreState;
    }

    public void setIsPreState(Integer isPreState) {
        this.isPreState = isPreState;
    }

    public String getCreateUserUuid() {
        return createUserUuid;
    }

    public void setCreateUserUuid(String createUserUuid) {
        this.createUserUuid = createUserUuid;
    }

    public Integer getNewState() {
        return newState;
    }

    public void setNewState(Integer newState) {
        this.newState = newState;
    }

    public Integer getVideoSpeed() {
        return videoSpeed;
    }

    public void setVideoSpeed(Integer videoSpeed) {
        this.videoSpeed = videoSpeed;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getAnalyBeginDate() {
        return analyBeginDate;
    }

    public void setAnalyBeginDate(Date analyBeginDate) {
        this.analyBeginDate = analyBeginDate;
    }

    public Date getAnalyEndDate() {
        return analyEndDate;
    }

    public void setAnalyEndDate(Date analyEndDate) {
        this.analyEndDate = analyEndDate;
    }

    public String getAnalyBeginDateStr() {

        if (analyBeginDate != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(analyBeginDate);
        } else {
            return "";
        }
    }

    public void setAnalyBeginDateStr(String analyBeginDateStr) {
        this.analyBeginDateStr = analyBeginDateStr;
    }

    public String getAnalyEndDateStr() {
        if (analyEndDate != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(analyEndDate);
        } else {
            return "";
        }
    }

    public void setAnalyEndDateStr(String analyEndDateStr) {
        this.analyEndDateStr = analyEndDateStr;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state == null ? 0 : state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public java.util.Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(java.util.Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getBeginDateStr() {
        if (beginDate != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(beginDate);
        } else {
            return "";
        }
    }

    public void setBeginDateStr(String beginDateStr) throws Exception {
        if (beginDateStr != null && !beginDateStr.trim().equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            this.beginDate = sdf.parse(beginDateStr);
        } else
            this.beginDate = null;
    }

    public java.util.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    public String getEndDateStr() {
        if (endDate != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(endDate);
        } else {
            return "";
        }
    }

    public void setEndDateStr(String endDateStr) throws Exception {
        if (endDateStr != null && !endDateStr.trim().equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            this.endDate = sdf.parse(endDateStr);
        } else
            this.endDate = null;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRunWeek() {
        return StringUtils.orderString(runWeek);
    }

    public void setRunWeek(String runWeek) {
        this.runWeek = runWeek;
    }

    public String getAnalyStartTime() {
        return analyStartTime;
    }

    public void setAnalyStartTime(String analyStartTime) {
        this.analyStartTime = analyStartTime;
    }

    public String getAnalyEndTime() {
        return analyEndTime;
    }

    public void setAnalyEndTime(String analyEndTime) {
        this.analyEndTime = analyEndTime;
    }


    public String getAnalyType() {
        return StringUtils.orderString(analyType);
    }

    public void setAnalyType(String analyType) {
        this.analyType = analyType;
    }

    public Integer getAutoStart() {
        return autoStart;
    }

    public void setAutoStart(Integer autoStart) {
        this.autoStart = autoStart;
    }

    public Integer getIsDeleted() {
        return isDeleted == null ? 0 : isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        if (createTime != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(createTime);
        } else {
            return "";
        }
    }

    public void setCreateTimeStr(String createTimeStr) throws Exception {
        if (createTimeStr != null && !createTimeStr.trim().equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            this.createTime = sdf.parse(createTimeStr);
        } else
            this.createTime = null;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public java.util.Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(java.util.Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyTimeStr() {
        if (modifyTime != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(modifyTime);
        } else {
            return "";
        }
    }

    public void setModifyTimeStr(String modifyTimeStr) throws Exception {
        if (modifyTimeStr != null && !modifyTimeStr.trim().equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            this.modifyTime = sdf.parse(modifyTimeStr);
        } else
            this.modifyTime = null;
    }

    public String getStateTag() {
        return Constants.TASK_STAT_MAP.get(state);
    }

    public void setStateTag(String stateTag) {
        this.stateTag = stateTag;
    }

    public String getTypeTag() {
        return Constants.TASK_TYPE.get(type);
    }

    public void setTypeTag(String typeTag) {
        this.typeTag = typeTag;
    }

    public String getAnalyTypeTag() {
        if (StringUtils.isEmptyOrNull(analyType)) {
            return "";
        } else {
            List<String> strings = Arrays.asList(analyType.split(","));
            List<String> anayTypes = strings.stream().map(a -> Constants.CAP_ANALY_TYPE.get(Integer.parseInt(a))).collect(Collectors.toList());
            StringBuilder b = new StringBuilder();
            for (String s : anayTypes) {
                b.append(s + "、");
            }
            return b.toString().substring(0, b.toString().length() - 1);
        }
    }

    public void setAnalyTypeTag(String analyTypeTag) {
        this.analyTypeTag = analyTypeTag;
    }

    @Override
    public String toString() {
        return "Task{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", state=" + state +
                ", beginDate=" + beginDate +
                ", beginDateStr='" + getBeginDateStr() + '\'' +
                ", endDate=" + endDate +
                ", endDateStr='" + getEndDateStr() + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", runWeek='" + runWeek + '\'' +
                ", analyStartTime='" + analyStartTime + '\'' +
                ", analyEndTime='" + analyEndTime + '\'' +
                ", analyType='" + analyType + '\'' +
                ", analyTypeTag='" + analyTypeTag + '\'' +
                ", autoStart=" + autoStart +
                ", isDeleted=" + isDeleted +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", createTimeStr='" + createTimeStr + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime=" + modifyTime +
                ", modifyTimeStr='" + modifyTimeStr + '\'' +
                ", memo='" + memo + '\'' +
                ", stateTag='" + stateTag + '\'' +
                ", typeTag='" + typeTag + '\'' +
                ", state=" + state +
                '}';
    }
}