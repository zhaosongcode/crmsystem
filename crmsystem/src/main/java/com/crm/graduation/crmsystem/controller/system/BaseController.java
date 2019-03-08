package com.crm.graduation.crmsystem.controller.system;

import com.crm.graduation.crmsystem.entity.system.user.CrmUser;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import com.crm.graduation.crmsystem.model.utils.Datas;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础controller之后都继承它
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取map
     */
    public Map getMap(){
        return new HashMap();
    }

    /**
     * 获得Datas对象
     * @return
     */
    public Datas getDatas(){
        return new Datas(this.getRequest());
    }

    /**
     * 获得request对象
     * @return
     */
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 清理session
     */
    protected void removeSession(){
        Session session = SecurityUtils.getSubject().getSession();
        session.removeAttribute(Consts.SESSION_USER);
    }

    /**
     * 获取当前登录用户的用户id
     * @return
     */
    protected String getCurrenUserId(){
        Session session = SecurityUtils.getSubject().getSession();
        CrmUser sessionUser = (CrmUser) session.getAttribute(Consts.SESSION_USER);
        String userId = sessionUser.getUserId();
        return userId;
    }

    /**
     * 获取session
     * @return
     */
    protected Session getSession(){
        Session session = SecurityUtils.getSubject().getSession();
        return session;
    }
}
