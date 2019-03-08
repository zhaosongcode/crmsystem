package com.crm.graduation.crmsystem.entity.other;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "crm_feedback")
@Getter
@Setter
public class CrmFeedback {

    /**
     * 反馈id
     */
    @Column(name = "feedback_id")
    private String feedbackId;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 解决状态
     */
    @Column(name = "solve_state")
    private String solveState;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 反馈类型
     */
    @Column(name = "feedback_type")
    private String feedbackType;

    /**
     * 反馈主题
     */
    @Column(name = "feedback_subject")
    private String feedbackSubject;

    /**
     * 反馈描述
     */
    @Column(name = "feedback_desc")
    private String feedbackDesc;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;
}
