package com.sensing.core.bean;

import java.io.Serializable;
import java.util.List;

public class SysResourceResp implements Serializable {

    private Integer id;
    private String title;
    private Integer parentId;
    private String searchCode;
    private List<SysResourceResp> childRseoList;

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

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public List<SysResourceResp> getChildRseoList() {
        return childRseoList;
    }

    public void setChildRseoList(List<SysResourceResp> childRseoList) {
        this.childRseoList = childRseoList;
    }
}
