package com.sensing.core.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wenbo
 */
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String roleName;
    private String roleDesc;
    private Integer parentId;
    private Date createTime;
    private String createTimeStr;
    private Integer isDeleted;
    private Integer roleCount;
    private String  addUuId;
    private String  addUserName;

    public String getAddUserName() {
        return addUserName;
    }

    public void setAddUserName(String addUserName) {
        this.addUserName = addUserName;
    }

    public String getAddUuId() {
        return addUuId;
    }

    public void setAddUuId(String addUuId) {
        this.addUuId = addUuId;
    }

    public Integer getRoleCount() {
        return roleCount;
    }

    public void setRoleCount(Integer roleCount) {
        this.roleCount = roleCount;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateTimeStr() {
        if (createTime != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(createTime);
        } else {
            return "";
        }
    }

    public void setCreateTimeStr(String createTimeStr) throws Exception {
        if (createTimeStr != null && !createTimeStr.trim().equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            this.createTime = sdf.parse(createTimeStr);
        } else
            this.createTime = null;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getId() {
        return id == null ? 0 : id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}