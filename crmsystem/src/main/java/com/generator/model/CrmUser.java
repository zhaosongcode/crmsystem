package com.generator.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "crm_user")
public class CrmUser {
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户密码
     */
    @Column(name = "user_password")
    private String userPassword;

    /**
     * 用户手机号
     */
    @Column(name = "user_phone")
    private String userPhone;

    /**
     * 用户邮箱
     */
    @Column(name = "user_email")
    private String userEmail;

    /**
     * 用户创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 最新修改时间
     */
    @Column(name = "updata_time")
    private Date updataTime;

    /**
     * 是否删除 (0/否 1/是)
     */
    @Column(name = "is_delete")
    private String isDelete;

    /**
     * 部门
     */
    @Column(name = "user_department")
    private String userDepartment;

    /**
     * 工种
     */
    @Column(name = "user_typeOfWork")
    private String userTypeofwork;

    /**
     * 工号
     */
    @Column(name = "user_code")
    private String userCode;

    /**
     * 真实姓名
     */
    @Column(name = "user_realName")
    private String userRealname;

    /**
     * 账号金币
     */
    @Column(name = "user_gold")
    private String userGold;

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
     * 获取用户名称
     *
     * @return user_name - 用户名称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名称
     *
     * @param userName 用户名称
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取用户密码
     *
     * @return user_password - 用户密码
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * 设置用户密码
     *
     * @param userPassword 用户密码
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * 获取用户手机号
     *
     * @return user_phone - 用户手机号
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * 设置用户手机号
     *
     * @param userPhone 用户手机号
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * 获取用户邮箱
     *
     * @return user_email - 用户邮箱
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * 设置用户邮箱
     *
     * @param userEmail 用户邮箱
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * 获取用户创建时间
     *
     * @return create_time - 用户创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置用户创建时间
     *
     * @param createTime 用户创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取最新修改时间
     *
     * @return updata_time - 最新修改时间
     */
    public Date getUpdataTime() {
        return updataTime;
    }

    /**
     * 设置最新修改时间
     *
     * @param updataTime 最新修改时间
     */
    public void setUpdataTime(Date updataTime) {
        this.updataTime = updataTime;
    }

    /**
     * 获取是否删除 (0/否 1/是)
     *
     * @return is_delete - 是否删除 (0/否 1/是)
     */
    public String getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除 (0/否 1/是)
     *
     * @param isDelete 是否删除 (0/否 1/是)
     */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取部门
     *
     * @return user_department - 部门
     */
    public String getUserDepartment() {
        return userDepartment;
    }

    /**
     * 设置部门
     *
     * @param userDepartment 部门
     */
    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    /**
     * 获取工种
     *
     * @return user_typeOfWork - 工种
     */
    public String getUserTypeofwork() {
        return userTypeofwork;
    }

    /**
     * 设置工种
     *
     * @param userTypeofwork 工种
     */
    public void setUserTypeofwork(String userTypeofwork) {
        this.userTypeofwork = userTypeofwork;
    }

    /**
     * 获取工号
     *
     * @return user_code - 工号
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * 设置工号
     *
     * @param userCode 工号
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * 获取真实姓名
     *
     * @return user_realName - 真实姓名
     */
    public String getUserRealname() {
        return userRealname;
    }

    /**
     * 设置真实姓名
     *
     * @param userRealname 真实姓名
     */
    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    /**
     * 获取账号金币
     *
     * @return user_gold - 账号金币
     */
    public String getUserGold() {
        return userGold;
    }

    /**
     * 设置账号金币
     *
     * @param userGold 账号金币
     */
    public void setUserGold(String userGold) {
        this.userGold = userGold;
    }
}