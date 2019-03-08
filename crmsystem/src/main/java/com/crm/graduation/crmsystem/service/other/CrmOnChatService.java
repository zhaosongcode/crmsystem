package com.crm.graduation.crmsystem.service.other;

import com.crm.graduation.crmsystem.dao.mapper.user.CrmUserMapper;
import com.crm.graduation.crmsystem.entity.dict.CrmDataDict;
import com.crm.graduation.crmsystem.entity.system.user.CrmUser;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import com.crm.graduation.crmsystem.model.vo.other.ContactSimpleVO;
import com.crm.graduation.crmsystem.model.vo.other.ContactVO;
import com.crm.graduation.crmsystem.service.dict.CrmDictService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CrmOnChatService {

    @Resource
    private CrmDictService crmDictService;

    @Resource
    private CrmUserMapper crmUserMapper;

    /**
     * 查询所有联系人
     */
    public List<ContactVO> getContacts(String userId)throws Exception{
        List<ContactVO> contacts = new ArrayList<>();
        //先查询所有部门
        CrmDataDict dictByCode = crmDictService.getDictByCode(Consts.DICT_DEPARTMENT_TYPE_CODE);
        String partentId = dictByCode.getDictId();
        //部门集合
        List<CrmDataDict> apartments = crmDictService.getListDict(partentId);
        for(CrmDataDict crmDataDict : apartments){
            ContactVO contactVO = new ContactVO();
            contactVO.setApartmentId(crmDataDict.getDictCode());
            contactVO.setApartmentName(crmDataDict.getDictName());
            contacts.add(contactVO);
        }
        //每个部门的用户集合
        for(ContactVO contactVO : contacts){
            List<ContactSimpleVO> contactSimpleVOS = new ArrayList<>();
            String userDepartment = contactVO.getApartmentId();
            Example example = new Example(CrmUser.class);
            example.setOrderByClause("create_time asc");
            example.createCriteria().andEqualTo("isDelete",0).andEqualTo("userDepartment",userDepartment).andNotEqualTo("userId",userId);
            List<CrmUser> users = crmUserMapper.selectByExample(example);
            for(CrmUser crmUser1 : users){
                ContactSimpleVO contactSimpleVO = new ContactSimpleVO();
                BeanUtils.copyProperties(crmUser1,contactSimpleVO);
                contactSimpleVOS.add(contactSimpleVO);
            }
            contactVO.setUsers(contactSimpleVOS);
            contactVO.setPeoCount(users.size());
        }
        return contacts;
    }
}
