package com.crm.graduation.crmsystem.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义密码器
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    private Logger logger = LoggerFactory.getLogger(CredentialsMatcher.class);

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
        String source = String.valueOf(usernamePasswordToken.getPassword());
        String salt = usernamePasswordToken.getUsername();
        int hashIterations = 10;
        SimpleHash simpleHash = new SimpleHash("MD5",source,salt,hashIterations);
        String s = simpleHash.toHex();
        logger.info("加密后的密码是 :"+s);
        //获得数据库中的密码
        String dbPassword = (String) getCredentials(info);
        logger.info("数据库密码为：" + dbPassword);
        //进行密码的比对
        return this.equals(s, dbPassword);
    }
}
