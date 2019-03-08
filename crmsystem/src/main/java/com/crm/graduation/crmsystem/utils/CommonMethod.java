package com.crm.graduation.crmsystem.utils;

import com.crm.graduation.crmsystem.entity.dict.CrmDataDict;
import com.crm.graduation.crmsystem.entity.dict.CrmDictProvince;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import com.crm.graduation.crmsystem.service.dict.CrmDictService;
import org.springframework.ui.ModelMap;

import java.util.List;

/**
 * 一些通用方法
 */
public class CommonMethod {

    /**
     * 字典
     */
    public void getDict(ModelMap modelMap,CrmDictService dictService) throws Exception{
        List<CrmDictProvince> address = dictService.getAllAddress();
        modelMap.addAttribute("address",address);
        CrmDataDict crmDataDict = dictService.getDictByCode(Consts.DICT_CLIENT_TYPE_CODE);
        List<CrmDataDict> clientType = null;
        if(crmDataDict != null){
            String partenId = crmDataDict.getDictId();
            //根据父id查询子字典
            clientType = dictService.getListDict(partenId);
        }
        modelMap.addAttribute("clientType",clientType);
    }
}
