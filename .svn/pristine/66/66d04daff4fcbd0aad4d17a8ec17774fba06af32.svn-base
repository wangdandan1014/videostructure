package com.sensing.core.bean.message;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_message_log
 *
 * @author
 */
public class SysMessageLog implements Serializable {
    private Integer id;

    /**
     * 类型： 1：申请列表的审批通过；2：申请列表的审批不通过； 3：审批列表的待审批；
     */
    private Integer type;

    /**
     * 未读的消息的uuid
     */
    private String extendUuId;

    /**
     * 用户的uuid
     */
    private String userUuid;

    /**
     * 读标记
     * 0:未读
     * 1:已读
     */
    private Integer isRead;

    /**
     * 删除标记
     * 0:删除
     * 1:已删除
     */
    private Integer isDel;

    /**
     * 创建时间戳
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExtendUuId() {
        return extendUuId;
    }

    public void setExtendUuId(String extendUuId) {
        this.extendUuId = extendUuId;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SysMessageLog() {
    }

    public SysMessageLog(Integer type, String extendUuId, String userUuid, Integer isRead, Integer isDel, Date createTime) {
        this.type = type;
        this.extendUuId = extendUuId;
        this.userUuid = userUuid;
        this.isRead = isRead;
        this.isDel = isDel;
        this.createTime = createTime;
    }
}