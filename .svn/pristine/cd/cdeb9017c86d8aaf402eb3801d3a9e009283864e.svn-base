package com.sensing.core.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author wenbo
 */
public class SysResource implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String title;
    private Integer parentId;
    private Integer type;
    private String path;
    private Integer orde;
    private String searchCode;
    private String projectType;//所属项目的类型  0：全部 ；1：行人 ；2：机动车；3：行人机动车
    private String memo;
    private String method;
    private List<SysResource> childResoList;
    private Integer isDel;

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    private boolean checkState;//当前用户是否有这项权限

    public List<SysResource> getChildResoList() {
        return childResoList;
    }

    public void setChildResoList(List<SysResource> childResoList) {
        this.childResoList = childResoList;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getOrde() {
        return orde == null ? 0 : orde;
    }

    public void setOrde(Integer orde) {
        this.orde = orde;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public SysResource(Integer id, String title, Integer parentId) {
        this.id = id;
        this.title = title;
        this.parentId = parentId;
    }

    public SysResource() {
    }

    public boolean isCheckState() {
        return checkState;
    }

    public void setCheckState(boolean checkState) {
        this.checkState = checkState;
    }
}