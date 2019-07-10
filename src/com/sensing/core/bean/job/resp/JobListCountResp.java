package com.sensing.core.bean.job.resp;

import java.io.Serializable;

public class JobListCountResp implements Serializable {
    private Integer state;
    private Integer count;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public JobListCountResp(Integer state, Integer count) {
        this.state = state;
        this.count = count;
    }

    public JobListCountResp() {
    }
}
