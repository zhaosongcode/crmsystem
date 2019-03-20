package com.crm.graduation.crmsystem.entity.other;

import java.util.Date;
import javax.persistence.*;

@Table(name = "crm_top_up")
public class CrmTopUp {
    /**
     * 支付id
     */
    @Id
    @Column(name = "top_up_id")
    private String topUpId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 支付时间
     */
    private Date time;

    /**
     * 银行id
     */
    @Column(name = "bank_id")
    private String bankId;

    /**
     * 支付金额
     */
    @Column(name = "top_up_num")
    private String topUpNum;

    /**
     * 银行卡号
     */
    @Column(name = "bank_num")
    private String bankNum;

    /**
     * 获取支付id
     *
     * @return top_up_id - 支付id
     */
    public String getTopUpId() {
        return topUpId;
    }

    /**
     * 设置支付id
     *
     * @param topUpId 支付id
     */
    public void setTopUpId(String topUpId) {
        this.topUpId = topUpId;
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
     * 获取支付时间
     *
     * @return time - 支付时间
     */
    public Date getTime() {
        return time;
    }

    /**
     * 设置支付时间
     *
     * @param time 支付时间
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 获取银行id
     *
     * @return bank_id - 银行id
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * 设置银行id
     *
     * @param bankId 银行id
     */
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    /**
     * 获取支付金额
     *
     * @return top_up_num - 支付金额
     */
    public String getTopUpNum() {
        return topUpNum;
    }

    /**
     * 设置支付金额
     *
     * @param topUpNum 支付金额
     */
    public void setTopUpNum(String topUpNum) {
        this.topUpNum = topUpNum;
    }

    /**
     * 获取银行卡号
     *
     * @return bank_num - 银行卡号
     */
    public String getBankNum() {
        return bankNum;
    }

    /**
     * 设置银行卡号
     *
     * @param bankNum 银行卡号
     */
    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }
}