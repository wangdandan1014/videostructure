package com.sensing.core.bean;

import java.io.Serializable;

public class SysResoEdidReq implements Serializable {
    private String userUuid;
    private Integer roleId;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
