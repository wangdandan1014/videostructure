package com.sensing.core.bean;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class UserContext implements Serializable{
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
