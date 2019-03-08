package com.crm.graduation.crmsystem.controller.system.user;

import com.crm.graduation.crmsystem.entity.dict.CrmDataDict;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import com.crm.graduation.crmsystem.model.vo.system.user.SelectUserVo;
import com.crm.graduation.crmsystem.model.vo.system.user.UserVo;
import com.crm.graduation.crmsystem.service.dict.CrmDictService;
import com.crm.graduation.crmsystem.service.system.user.CrmUserService;
import com.crm.graduation.crmsystem.utils.ResultDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/main/user")
@Controller
public class CrmUserController {

    @Resource
    private CrmUserService crmUserService;

    @Resource
    private CrmDictService crmDictService;

    /**
     * 用户列表
     */
    @RequestMapping("/list")
    public String userList(ModelMap modelMap, SelectUserVo userVo){
        modelMap.addAttribute("userVo",userVo);
        try {
            //用户列表
            List<UserVo> users = crmUserService.getUserList(userVo);
            modelMap.addAttribute("users",users);
            //字典部门
            CrmDataDict dictByCode = crmDictService.getDictByCode(Consts.DICT_DEPARTMENT_TYPE_CODE);
            List<CrmDataDict> department = null;
            if(dictByCode!=null){
                department = crmDictService.getListDict(dictByCode.getDictId());
            }
            modelMap.addAttribute("department",department);
            //字典职位
            CrmDataDict dictByCode1 = crmDictService.getDictByCode(Consts.DICT_STAFF_TYPE_CODE);
            List<CrmDataDict> staff = null;
            if(dictByCode1!=null){
                staff = crmDictService.getListDict(dictByCode1.getDictId());
            }
            modelMap.addAttribute("staff",staff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "system/user/list";
    }

    /**
     * 删除用户
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public ResultDto deleteUser(String userId){
        try {
            String s = crmUserService.deleteUser(userId);
            if("success".equals(s)){
                return ResultDto.success();
            }else{
                return ResultDto.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error();
        }

    }

    /**
     * 前往添加用户
     */
    @RequestMapping("/addUser")
    public String toAddUser(ModelMap modelMap){
        try {
            //字典部门
            CrmDataDict dictByCode = crmDictService.getDictByCode(Consts.DICT_DEPARTMENT_TYPE_CODE);
            List<CrmDataDict> department = null;
            if(dictByCode!=null){
                department = crmDictService.getListDict(dictByCode.getDictId());
            }
            modelMap.addAttribute("department",department);
            //字典职位
            CrmDataDict dictByCode1 = crmDictService.getDictByCode(Consts.DICT_STAFF_TYPE_CODE);
            List<CrmDataDict> staff = null;
            if(dictByCode1!=null){
                staff = crmDictService.getListDict(dictByCode1.getDictId());
            }
            modelMap.addAttribute("staff",staff);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "system/user/add";
    }

    /**
     * 保存新增动作
     */
    @RequestMapping("/adduserDo")
    @ResponseBody
    public ResultDto addUser(UserVo userVo){
        try {
            String s = crmUserService.addUser(userVo);
            if("success".equals(s)){
                return ResultDto.success();
            }else{
                return ResultDto.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error();
        }
    }

    /**
     * 跳转编辑页面
     */
    @RequestMapping("/editUser")
    public String toEdit(String userId, ModelMap modelMap){
        try {
            UserVo user = crmUserService.getUser(userId);
            modelMap.addAttribute("user",user);
            //字典部门
            CrmDataDict dictByCode = crmDictService.getDictByCode(Consts.DICT_DEPARTMENT_TYPE_CODE);
            List<CrmDataDict> department = null;
            if(dictByCode!=null){
                department = crmDictService.getListDict(dictByCode.getDictId());
            }
            modelMap.addAttribute("department",department);
            //字典职位
            CrmDataDict dictByCode1 = crmDictService.getDictByCode(Consts.DICT_STAFF_TYPE_CODE);
            List<CrmDataDict> staff = null;
            if(dictByCode1!=null){
                staff = crmDictService.getListDict(dictByCode1.getDictId());
            }
            modelMap.addAttribute("staff",staff);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "system/user/edit";
    }

    /**
     * 实际修改业务操作
     */
    @RequestMapping("/editUserDo")
    @ResponseBody
    public ResultDto editUserDo(UserVo userVo){
        try {
            String s = crmUserService.editUser(userVo);
            return ResultDto.success(s);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error();
        }
    }

}
