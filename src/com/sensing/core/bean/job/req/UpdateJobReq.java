package com.sensing.core.bean.job.req;

import java.io.Serializable;

public class UpdateJobReq implements Serializable {

    private String jobUuid;
    private Integer ratifyResult;
    private String ratifyMemo;
    private String ratifyUser;
    private Integer state;
    private Integer source; //详情页的来源  申请列表：审批通过的详情1 ，审批不通过的详情2； 审批列表：待审批列表的详情3

    public Integer getSource() {
        return source == null ? 0 : source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getJobUuid() {
        return jobUuid;
    }

    public void setJobUuid(String jobUuid) {
        this.jobUuid = jobUuid;
    }

    public Integer getRatifyResult() {
        return ratifyResult;
    }

    public void setRatifyResult(Integer ratifyResult) {
        this.ratifyResult = ratifyResult;
    }

    public String getRatifyMemo() {
        return ratifyMemo;
    }

    public void setRatifyMemo(String ratifyMemo) {
        this.ratifyMemo = ratifyMemo;
    }

    public String getRatifyUser() {
        return ratifyUser;
    }

    public void setRatifyUser(String ratifyUser) {
        this.ratifyUser = ratifyUser;
    }
}
