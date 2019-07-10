package com.sensing.core.bean.job.resp;

import com.sensing.core.utils.Constants;

import java.io.Serializable;
import java.util.Date;

public class JobRatifyListResp implements Serializable {
    private static final long serialVersionUID = 1L;
    //uuid
    private String jobUuid;
    //名称
    private String name;
    //创建时间
    private Date createTime;
    private String createTimeStr;
    //创建人的名字
    private String createUserName;
    //创建人的时间
    private Date ratifyTime;
    private String ratifyTimeStr;
    //创建人的名字
    private String ratifyUserName;
    //审核类型，布控和撤控
    private Integer ratifyType;
    private String ratifyTypeStr;
    //审核结果  0:待审批 1：审批通过 2：审批未通过
    private Integer ratifyResult;
    private String ratifyResultStr;
    //布控状态状态  0:待开启 10:布控中  11:暂停中  12:已撤控  13:撤控中 14 已完成
    private Integer state;
    //1：行人   2：机动车   4：非机动车
    private Integer jobsType;
    private String jobsTypeStr;
    private Integer isRead; //标记当前数据的阅读状态 1：已读，0：未读
    private Integer level;
    private String levelStr;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelStr() {
        if (level == null || level.intValue() == 1) {
            return "一级";
        } else if (level.intValue() == 2) {
            return "二级";
        } else if (level.intValue() == 3) {
            return "三级";
        } else {
            return "未知";
        }
    }

    public void setLevelStr(String levelStr) {
        this.levelStr = levelStr;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getJobsTypeStr() {
        return Constants.CAP_ANALY_TYPE.get(jobsType);
    }

    public void setJobsTypeStr(String jobsTypeStr) {
        this.jobsTypeStr = jobsTypeStr;
    }

    public String getRatifyResultStr() {
        if (ratifyResult == null || ratifyResult.intValue() == 0) {
            return "待审批";
        } else if (ratifyResult.intValue() == 1) {
            return "审批通过";
        } else if (ratifyResult.intValue() == 2) {
            return "审批未通过";
        } else {
            return "未知";
        }
    }

    public void setRatifyResultStr(String ratifyResultStr) {
        this.ratifyResultStr = ratifyResultStr;
    }

    public Integer getRatifyType() {
        return ratifyType;
    }

    public void setRatifyType(Integer ratifyType) {
        this.ratifyType = ratifyType;
    }

    public String getRatifyTypeStr() {
        if (ratifyType == null || ratifyType.intValue() == 0) {
            return "布控";
        } else {
            return "撤控";
        }
    }

    public void setRatifyTypeStr(String ratifyTypeStr) {
        this.ratifyTypeStr = ratifyTypeStr;
    }

    public String getJobUuid() {
        return jobUuid;
    }

    public void setJobUuid(String jobUuid) {
        this.jobUuid = jobUuid;
    }

    public Integer getJobsType() {
        return jobsType;
    }

    public void setJobsType(Integer jobsType) {
        this.jobsType = jobsType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        if (createTime != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(createTime);
        } else {
            return "";
        }
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }


    public Date getRatifyTime() {
        return ratifyTime;
    }

    public void setRatifyTime(Date ratifyTime) {
        this.ratifyTime = ratifyTime;
    }

    public String getRatifyTimeStr() {

        if (ratifyTime != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(ratifyTime);
        } else {
            return "";
        }
    }

    public void setRatifyTimeStr(String ratifyTimeStr) {
        this.ratifyTimeStr = ratifyTimeStr;
    }

    public String getRatifyUserName() {
        return ratifyUserName;
    }

    public void setRatifyUserName(String ratifyUserName) {
        this.ratifyUserName = ratifyUserName;
    }


    public Integer getRatifyResult() {
        return ratifyResult;
    }

    public void setRatifyResult(Integer ratifyResult) {
        this.ratifyResult = ratifyResult;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
