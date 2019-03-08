package com.crm.graduation.crmsystem.entity.other;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
@Table(name = "crm_message")
public class CrmMessage implements Serializable {
    /**
     * 消息id
     */
    @Id
    @Column(name = "message_id")
    private String messageId;

    /**
     * 消息内容
     */
    @Column(name = "message_content")
    private String messageContent;

    /**
     * 来自用户id
     */
    @Column(name = "from_userId")
    private String fromUserid;

    /**
     * 发给用户id
     */
    @Column(name = "to_userId")
    private String toUserid;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 消息的状态01/已读 02/未读
     */
    @Column(name = "message_status")
    private String messageStatus;

    @Transient
    private String apartmentId;

    @Transient
    private String messageCount;

}