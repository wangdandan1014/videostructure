package com.sensing.core.bean;

import java.io.Serializable;

public class KeyValueTemp implements Serializable {
    private Long key;
    private Long value;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public KeyValueTemp(Long key, Long value) {
        this.key = key;
        this.value = value;
    }

    public KeyValueTemp() {
    }
}
