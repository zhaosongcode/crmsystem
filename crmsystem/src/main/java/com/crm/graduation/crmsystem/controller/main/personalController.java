package com.crm.graduation.crmsystem.controller.main;

import com.crm.graduation.crmsystem.entity.system.user.CrmUser;
import com.crm.graduation.crmsystem.model.utils.Result;
import com.crm.graduation.crmsystem.service.system.user.CrmUserService;
import com.crm.graduation.crmsystem.utils.Tools;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 个人
 */
@RequestMapping("/personal")
@Controller
public class personalController {

    @Resource
    private CrmUserService crmUserService;

    /**
     * 跳转个人设置页面
     */
    @RequestMapping("/personalSet")
    public String toPersonalSet(String userId, Model model) throws Exception {
        CrmUser user = null;
        try {
            user = crmUserService.getUserByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("personalMes",user);
        return "personal/personalSet";
    }

    /**
     * 修改个人信息
     */
    @RequestMapping("/updataPerMess")
    @ResponseBody
    public Result updataPerMes(String phone, String email, String userId) throws Exception {
        if(Tools.isEmpty(phone) || Tools.isEmpty(email) || Tools.isEmpty(userId)){
            return Result.returnObj(1,"姓名或邮箱不能为空");
        }else{
            CrmUser user = new CrmUser();
            user.setPhone(phone);
            user.setUserId(userId);
            user.setEmail(email);
            boolean b = crmUserService.updataUserMessage(user);
            if(b){
                return Result.returnObj(0,"success");
            }else{
                return Result.returnObj(1,"修改失败");
            }
        }
    }

    /**
     * 修改个人信息密码
     */
    @RequestMapping("/updataPerPass")
    @ResponseBody
    public Result upPass(String newPassword,String oldPassword,String affirmPassword,String userId) throws Exception {
        if(Tools.isEmpty(newPassword)||Tools.isEmpty(oldPassword)||Tools.isEmpty(affirmPassword)||Tools.isEmpty(userId)){
            return Result.returnObj(1,"请正确输入");
        }else{
            if(newPassword.equals(affirmPassword)){
                CrmUser user = crmUserService.getUserByUserId(userId);
                String userPassword = user.getUserPassword();//数据库中取出的密码
                String source = oldPassword;//旧密码加密
                String salt = user.getUserName();
                int hashIterations = 10;
                SimpleHash simpleHash = new SimpleHash("MD5",source,salt,hashIterations);
                String s = simpleHash.toHex();
                if(s.equals(userPassword)){
                    String sources = newPassword;
                    String salts = user.getUserName();
                    int hashIterationss = 10;
                    SimpleHash simpleHashs = new SimpleHash("MD5",sources,salts,hashIterationss);
                    String s1 = simpleHashs.toHex();
                    user.setUserPassword(s1);
                    //更改密码
                    boolean b = crmUserService.updataPassword(user);
                    if(b){
                        return Result.returnObj(0,"修改成功");
                    }else{
                        return Result.returnObj(1,"修改失败");
                    }
                }else{
                    return Result.returnObj(1,"您输入的密码不正确");
                }
            }else{
                return Result.returnObj(1,"两次输入密码不一致");
            }
        }
    }
}
