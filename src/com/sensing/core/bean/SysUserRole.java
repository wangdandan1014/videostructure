package com.sensing.core.bean;

import com.sensing.core.utils.Constants;

import java.io.Serializable;

/**
 * @author wenbo
 */
@SuppressWarnings("all")
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String userUuid;
    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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