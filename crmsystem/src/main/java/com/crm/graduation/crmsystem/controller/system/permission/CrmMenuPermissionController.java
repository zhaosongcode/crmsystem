package com.crm.graduation.crmsystem.controller.system.permission;

import com.crm.graduation.crmsystem.model.vo.system.permission.MenuVO;
import com.crm.graduation.crmsystem.service.system.permission.CrmPermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/main/permission")
public class CrmMenuPermissionController {

    @Resource
    private CrmPermissionService crmPermissionService;

    /**
     * 查询菜单
     */
    @RequestMapping(value = "/menu", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<MenuVO> getList(){
        List<MenuVO> menus = null;
        try {
            menus = crmPermissionService.getList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menus;
    }
}
