package com.crm.graduation.crmsystem.entity.route.tip;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Table(name = "crm_tip")
@Getter
@Setter
public class CrmTip {
    /**
     * 提醒事项id
     */
    @Id
    @Column(name = "tip_id")
    private String tipId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 提醒时间
     */
    @Column(name = "tip_time")
    private Date tipTime;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 提醒内容
     */
    @Column(name = "tip_content")
    private String tipContent;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private String isDelete;

    /**
     * 是否关闭提醒
     */
    @Column(name = "is_close")
    private String isClose;

    @Transient
    private String cTime;

    @Transient
    private String tTime;

    /**
     * 获取提醒事项id
     *
     * @return tip_id - 提醒事项id
     */
    public String getTipId() {
        return tipId;
    }

    /**
     * 设置提醒事项id
     *
     * @param tipId 提醒事项id
     */
    public void setTipId(String tipId) {
        this.tipId = tipId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取提醒时间
     *
     * @return tip_time - 提醒时间
     */
    public Date getTipTime() {
        return tipTime;
    }

    /**
     * 设置提醒时间
     *
     * @param tipTime 提醒时间
     */
    public void setTipTime(Date tipTime) {
        this.tipTime = tipTime;
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

    /**
     * 获取提醒内容
     *
     * @return tip_content - 提醒内容
     */
    public String getTipContent() {
        return tipContent;
    }

    /**
     * 设置提醒内容
     *
     * @param tipContent 提醒内容
     */
    public void setTipContent(String tipContent) {
        this.tipContent = tipContent;
    }

    /**
     * 获取是否删除
     *
     * @return is_delete - 是否删除
     */
    public String getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除
     *
     * @param isDelete 是否删除
     */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取是否关闭提醒
     *
     * @return is_close - 是否关闭提醒
     */
    public String getIsClose() {
        return isClose;
    }

    /**
     * 设置是否关闭提醒
     *
     * @param isClose 是否关闭提醒
     */
    public void setIsClose(String isClose) {
        this.isClose = isClose;
    }
}