package com.sensing.core.bean.job.req;

import java.io.Serializable;

public class UpdateOperateReq implements Serializable {

    private String userUuid;
    private String jobUuid;
    //布控状态 10:待启动 20:布控中  30:暂停中 40:休息中 50:已撤控  60:撤控中 70:已完成
    private Integer state;
    //订阅状态 1：订阅 2取消订阅
    private Integer subscribeType;
    private Integer isDeleted;
    //申请撤控
    private Integer cancelJobs;
    private String cancelJobReason;
    //审核结果  0:待审批 1：审批通过 2：审批未通过
    private Integer ratifyResult;
    private Integer ratifyType;
    private String ratifyMemo;

    public String getRatifyMemo() {
        return ratifyMemo;
    }

    public void setRatifyMemo(String ratifyMemo) {
        this.ratifyMemo = ratifyMemo;
    }

    public Integer getRatifyType() {
        return ratifyType;
    }

    public void setRatifyType(Integer ratifyType) {
        this.ratifyType = ratifyType;
    }

    public Integer getRatifyResult() {
        return ratifyResult;
    }

    public void setRatifyResult(Integer ratifyResult) {
        this.ratifyResult = ratifyResult;
    }

    public String getCancelJobReason() {
        return cancelJobReason;
    }

    public void setCancelJobReason(String cancelJobReason) {
        this.cancelJobReason = cancelJobReason;
    }

    public Integer getCancelJobs() {
        return cancelJobs;
    }

    public void setCancelJobs(Integer cancelJobs) {
        this.cancelJobs = cancelJobs;
    }

    public Integer getIsDeleted() {
        return isDeleted ;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public Integer getSubscribeType() {
        return subscribeType;
    }

    public void setSubscribeType(Integer subscribeType) {
        this.subscribeType = subscribeType;
    }

    public String getJobUuid() {
        return jobUuid;
    }

    public void setJobUuid(String jobUuid) {
        this.jobUuid = jobUuid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
