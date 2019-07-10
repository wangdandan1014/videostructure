package com.sensing.core.bean;

import java.io.Serializable;


public class JobsTemplateDb implements Serializable {
    private String uuid;
    private String job_uuid;
    private Integer templatedb_id;
    private String obj_uuid;


    public String getObj_uuid() {
        return obj_uuid;
    }

    public void setObj_uuid(String obj_uuid) {
        this.obj_uuid = obj_uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getJob_uuid() {
        return job_uuid;
    }

    public void setJob_uuid(String job_uuid) {
        this.job_uuid = job_uuid;
    }

    public Integer getTemplatedb_id() {
        return templatedb_id;
    }

    public void setTemplatedb_id(Integer templatedb_id) {
        this.templatedb_id = templatedb_id;
    }
}
