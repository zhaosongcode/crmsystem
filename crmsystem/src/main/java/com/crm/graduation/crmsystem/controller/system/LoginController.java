package com.crm.graduation.crmsystem.controller.system;

import com.crm.graduation.crmsystem.entity.system.user.CrmUser;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import com.crm.graduation.crmsystem.model.utils.Datas;
import com.crm.graduation.crmsystem.model.utils.Result;
import com.crm.graduation.crmsystem.model.vo.system.UserResourcesVo;
import com.crm.graduation.crmsystem.service.system.user.CrmUserService;
import com.crm.graduation.crmsystem.service.system.permission.UserPermissinService;
import com.crm.graduation.crmsystem.utils.Tools;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 */
@Controller
public class LoginController extends BaseController {

    @Resource
    private CrmUserService userService;

    @Resource
    private UserPermissinService userPermissinService;

    /**
     * 跳转登陆页面
     * @return
     */
    @RequestMapping(value = "/login")
    public String Login(){
        return "login";
    }

    /**
     * 登陆
     * @return
     */
    @RequestMapping(value = "/login_login",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object login(){
        String errInfo = null;
        String userId = null;
        Map<String,String> map = new HashMap<>();
        Datas datas = this.getDatas();
        String keyData[] = datas.getString("datas").split(",boke,");
        if(keyData.length == 3){
            Session session = this.getSession();
            String sessionCode = (String)session.getAttribute(Consts.SESSION_CODE);//获取session中的验证码
            String code = keyData[2];
            if(null == code || "".equals(code)){//判断效验码
                errInfo = "验证码不能为空";//效验码为空
            }else{
                String USERNAME = keyData[0];	//登录过来的用户名
                String PASSWORD  = keyData[1];	//登录过来的密码
                datas.put("USERNAME", USERNAME);
                if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){		//判断登录验证码
                    //shiro加入身份验证
                    Subject subject = SecurityUtils.getSubject();
                    UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
                    subject.logout();
                    try {
                        subject.login(token);
                    } catch (Exception e) {
                        errInfo = "账号或密码错误";
                    }
                }else{
                    errInfo = "验证码错误";//验证码输入有误
                }
                if(Tools.isEmpty(errInfo)){
                    errInfo = "success";//验证成功
                    logger.info(USERNAME + "登录系统");
                    CrmUser user = userService.getUserByUserName(USERNAME);
                    map.put("userId",user.getUserId());
                }
            }
        }else{
            errInfo = "输入不得为空";	//缺少参数
        }
        map.put("result", errInfo);
        return Result.returnObject(new Datas(), map);
    }

    /**
     * 跳转主页面
     */
    @RequestMapping(value = "/main/index")
    public String tomain(){
        return "index";
    }

    @RequestMapping("/main/test")
    public String test(){
        return "test";
    }

    @RequestMapping("/")
    public String indexindex(){
        return "redirect:main/index";
    }

    /**
     * 主页面前加载
     */
    @RequestMapping("/main/load")
    @ResponseBody
    public Result loadIndex(String userId) throws Exception{
        UserResourcesVo userPermissions = userPermissinService.getUserPermission(userId);
        if(userPermissions != null){
            getSession().setAttribute("menus",userPermissions);
            //model.addAttribute("menus",userPermissions);
            return Result.returnObj(0,"success");
        }else{
            return Result.returnObj(1,"初始化失败");
        }
    }
}
