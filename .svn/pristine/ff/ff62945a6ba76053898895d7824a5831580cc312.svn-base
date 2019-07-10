package com.sensing.core.utils.task;

import java.util.List;

public class ChannelBean {
    private List<Integer> newAnalyType;//新的抓拍类型 1 3 4
    private String channelUuid;
    private String extendUuid;// 当前通道关联的实施结构化或者布控任务的uuid
    private Integer isJobs;// 是否是布控任务（布控不要往kafka里写数据）
    private List<Integer> nowAnalyType;// 当前的抓拍类型


    public String getExtendUuid() {
        return extendUuid;
    }

    public void setExtendUuid(String extendUuid) {
        this.extendUuid = extendUuid;
    }

    public Integer getIsJobs() {
        return isJobs;
    }

    public void setIsJobs(Integer isJobs) {
        this.isJobs = isJobs;
    }

    public List<Integer> getNewAnalyType() {
        return newAnalyType;
    }

    public void setNewAnalyType(List<Integer> newAnalyType) {
        this.newAnalyType = newAnalyType;
    }

    public List<Integer> getNowAnalyType() {
        return nowAnalyType;
    }

    public void setNowAnalyType(List<Integer> nowAnalyType) {
        this.nowAnalyType = nowAnalyType;
    }

    public String getChannelUuid() {
        return channelUuid;
    }

    public void setChannelUuid(String channelUuid) {
        this.channelUuid = channelUuid;
    }

    @Override
    public String toString() {
        return "ChannelBean{" +
                "newAnalyType=" + newAnalyType +
                ", channelUuid='" + channelUuid + '\'' +
                ", extendUuid='" + extendUuid + '\'' +
                ", isJobs=" + isJobs +
                ", nowAnalyType=" + nowAnalyType +
                '}';
    }
}
