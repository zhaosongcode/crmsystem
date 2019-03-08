package com.generator.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "crm_feedback")
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
     * 解决状态(01/未阅读 02/正在处理 03/已解决)
     */
    @Column(name = "solve_state")
    private String solveState;

    /**
     * 是否删除(0/否 1/是)
     */
    @Column(name = "is_delete")
    private Boolean isDelete;

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

    /**
     * 获取反馈id
     *
     * @return feedback_id - 反馈id
     */
    public String getFeedbackId() {
        return feedbackId;
    }

    /**
     * 设置反馈id
     *
     * @param feedbackId 反馈id
     */
    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取解决状态(01/未阅读 02/正在处理 03/已解决)
     *
     * @return solve_state - 解决状态(01/未阅读 02/正在处理 03/已解决)
     */
    public String getSolveState() {
        return solveState;
    }

    /**
     * 设置解决状态(01/未阅读 02/正在处理 03/已解决)
     *
     * @param solveState 解决状态(01/未阅读 02/正在处理 03/已解决)
     */
    public void setSolveState(String solveState) {
        this.solveState = solveState;
    }

    /**
     * 获取是否删除(0/否 1/是)
     *
     * @return is_delete - 是否删除(0/否 1/是)
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除(0/否 1/是)
     *
     * @param isDelete 是否删除(0/否 1/是)
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取反馈类型
     *
     * @return feedback_type - 反馈类型
     */
    public String getFeedbackType() {
        return feedbackType;
    }

    /**
     * 设置反馈类型
     *
     * @param feedbackType 反馈类型
     */
    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    /**
     * 获取反馈主题
     *
     * @return feedback_subject - 反馈主题
     */
    public String getFeedbackSubject() {
        return feedbackSubject;
    }

    /**
     * 设置反馈主题
     *
     * @param feedbackSubject 反馈主题
     */
    public void setFeedbackSubject(String feedbackSubject) {
        this.feedbackSubject = feedbackSubject;
    }

    /**
     * 获取反馈描述
     *
     * @return feedback_desc - 反馈描述
     */
    public String getFeedbackDesc() {
        return feedbackDesc;
    }

    /**
     * 设置反馈描述
     *
     * @param feedbackDesc 反馈描述
     */
    public void setFeedbackDesc(String feedbackDesc) {
        this.feedbackDesc = feedbackDesc;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}