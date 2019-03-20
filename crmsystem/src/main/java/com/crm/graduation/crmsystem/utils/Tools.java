package com.crm.graduation.crmsystem.utils;


import com.crm.graduation.crmsystem.entity.system.user.CrmUser;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 工具类
 */
public class Tools {

    public static void main(String[] args) {
        System.out.println(get32UUID());
    }

    /**
     * 获取32位UUID
     * @return
     */
    public static String get32UUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }

    /**
     * 判断字符创是空
     */
    public static boolean isEmpty(String s){
        if(s==null||(s.trim()).equals("")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断字符串不为空
     */
    public static boolean notEmpty(String s){
        if(s==null||(s.trim()).equals("")){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 保留三位小数
     * @param d
     * @return
     */
    public static double keepThreePoint(double d){
        DecimalFormat df = new DecimalFormat("#.000");
        String s = df.format(d);
        double v = Double.parseDouble(s);
        return v;
    }

    /**
     * 保留两位小数
     */
    public static double keepTwoPoint(double d){
        DecimalFormat df = new DecimalFormat("#.00");
        String s = df.format(d);
        double v = Double.parseDouble(s);
        return v;
    }

    /**
     * 时间格式转换
     */
    public static String tranTime(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = formatter.format(date);
        return format;
    }
    /**
     * 密码加密
     */
    public static String passwordEncry(String oldPassword, String userName){
        int hashIterationss = 10;
        SimpleHash simpleHashs = new SimpleHash("MD5",oldPassword,userName,hashIterationss);
        String encyPassword = simpleHashs.toHex();
        return encyPassword;
    }
}
