package com.crm.graduation.crmsystem.controller.system.role;

import com.crm.graduation.crmsystem.entity.system.role.CrmRole;
import com.crm.graduation.crmsystem.model.vo.system.permission.AddRoleVO;
import com.crm.graduation.crmsystem.model.vo.system.permission.PermissionVO;
import com.crm.graduation.crmsystem.service.system.role.CrmRoleService;
import com.crm.graduation.crmsystem.utils.ResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/main/role")
public class CrmRoleController {

    private static String VIEW = "system/role/";

    private static Logger log = LoggerFactory.getLogger(CrmRoleController.class);

    @Resource
    private CrmRoleService crmRoleService;

    /**
     * 跳转list页面
     */
    @RequestMapping("/list")
    public String list(ModelMap modelMap,CrmRole crmRole){
        modelMap.addAttribute("role",crmRole);
        //查询角色
        try {
            List<CrmRole> roles = crmRoleService.getList(crmRole);
            modelMap.addAttribute("roles",roles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return VIEW + "list";
    }

    /**
     * 删除角色
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResultDto deleteRole(String roleIds){
        try {
            String s = crmRoleService.deleteRole(roleIds);
            return ResultDto.success(s);
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.error();
        }
    }

    /**
     * 前往新增页面
     */
    @RequestMapping("/add")
    public String toAdd(){
        return VIEW + "add";
    }

    /**
     * 新增操作
     */
    @RequestMapping("/addDo")
    @ResponseBody
    public ResultDto addDo(AddRoleVO addRoleVO){
        try {
            String s = crmRoleService.addDo(addRoleVO);
            return ResultDto.success(s);
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.error();
        }
    }

    /**
     * 前往编辑页面
     */
    @RequestMapping("/edit")
    public String toEdit(String roleId, ModelMap modelMap){
        //查询角色的相关信息
        try {
            AddRoleVO role = crmRoleService.getRole(roleId);
            String permissionIds = role.getPermissionIds();
            String[] split = permissionIds.split(",");
            List<PermissionVO> menus = new ArrayList<>();
            for(String permissionId : split){
                PermissionVO permissionVO = new PermissionVO();
                permissionVO.setPermissionId(permissionId);
                menus.add(permissionVO);
            }
            modelMap.addAttribute("menu",menus);
            modelMap.addAttribute("roleName",role.getRoleName());
            modelMap.addAttribute("roleId",role.getRoleId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return VIEW + "edit";
    }

    /**
     * 修改保存动作
     */
    @RequestMapping("/editDo")
    @ResponseBody
    public ResultDto editDo(AddRoleVO addRoleVO){
        try {
            String s = crmRoleService.editDo(addRoleVO);
            return ResultDto.success(s);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error();
        }
    }
}
