package com.sensing.core.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author wenbo
 */
public class SysResourceCheck extends SysResource {
    private static final long serialVersionUID = 1L;
    private boolean checkState;

    public boolean isCheckState() {
        return checkState;
    }

    public void setCheckState(boolean checkState) {
        this.checkState = checkState;
    }
}