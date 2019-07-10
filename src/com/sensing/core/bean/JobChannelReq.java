package com.sensing.core.bean;

import java.util.List;

public class JobChannelReq extends BaseRequest {
    private List<String> channelUuidList;
    private String createUser;
    private String modifyUser;
    private String jobUuid;

    public String getJobUuid() {
        return jobUuid;
    }

    public void setJobUuid(String jobUuid) {
        this.jobUuid = jobUuid;
    }

    public List<String> getChannelUuidList() {
        return channelUuidList;
    }

    public void setChannelUuidList(List<String> channelUuidList) {
        this.channelUuidList = channelUuidList;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }
}
