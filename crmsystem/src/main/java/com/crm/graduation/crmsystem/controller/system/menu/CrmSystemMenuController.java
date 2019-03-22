package com.crm.graduation.crmsystem.controller.system.menu;

import com.crm.graduation.crmsystem.entity.system.permission.CrmPermission;
import com.crm.graduation.crmsystem.model.vo.system.menu.MenuVo;
import com.crm.graduation.crmsystem.service.system.menu.CrmSystemMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/main/menu")
public class CrmSystemMenuController {

    @Resource
    private CrmSystemMenuService crmSystemMenuService;

    /**
     * 跳转菜单页面
     */
    @RequestMapping("/list")
    public String index(ModelMap map, MenuVo menuVo){
        map.addAttribute("menuVo",menuVo);
        try {
            List<MenuVo> menus = crmSystemMenuService.getList(menuVo);
            map.addAttribute("menus",menus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "system/menu/list";
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(String menuIds){
        String mess = "fail";
        if(menuIds != null){
            String[] split = menuIds.split(",");
            if(split.length>1){
                //批量删除
               mess = crmSystemMenuService.dele(split);
            }else{
                //单个删除
                mess = crmSystemMenuService.deles(split);
            }
        }
        return mess;
    }

    /**
     * 跳转添加页面
     */
    @RequestMapping("/add")
    public String toAdd(ModelMap map){
        List<CrmPermission> permissions = crmSystemMenuService.getPaMenu();
        map.addAttribute("paMenus",permissions);
        return "system/menu/add";
    }

    /**
     * 保存动作
     */
    @RequestMapping("/save")
    @ResponseBody
    public String save(MenuVo menuVo){
        String mess = "fail";
        if(menuVo != null){
            mess = crmSystemMenuService.save(menuVo);
        }
        return mess;
    }

    /**
     * 跳转编辑页面
     */
    @RequestMapping("/edit")
    public String toEdit(String menuId, ModelMap map){
        MenuVo menuVo = crmSystemMenuService.getPermissionById(menuId);
        map.addAttribute("menu",menuVo);
        List<CrmPermission> permissions = crmSystemMenuService.getPaMenu();
        map.addAttribute("paMenus",permissions);
        return "system/menu/edit";
    }

    /**
     * 修改动作
     */
    @RequestMapping("/editDo")
    @ResponseBody
    public String editDo(MenuVo menuVo){
        String mess = "success";
        if(menuVo != null){
            mess = crmSystemMenuService.editDo(menuVo);
        }
        return mess;
    }
}
