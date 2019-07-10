package com.sensing.core.bean;

import com.sensing.core.utils.Pager;

import java.util.List;

public class SysRoleReq extends Pager {

    private List<Integer> pIds;
    private String uuid;
    private Integer isAdmin;


    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<Integer> getpIds() {
        return pIds;
    }

    public void setpIds(List<Integer> pIds) {
        this.pIds = pIds;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
