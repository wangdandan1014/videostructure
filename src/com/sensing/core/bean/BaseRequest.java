package com.sensing.core.bean;

import java.io.Serializable;

public class BaseRequest implements Serializable {
    private String userUuid;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }
}
