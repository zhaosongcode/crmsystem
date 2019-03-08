package com.crm.graduation.crmsystem.service.system.user;

import com.crm.graduation.crmsystem.dao.DaoSupport;
import com.crm.graduation.crmsystem.dao.mapper.dict.CrmDictMapper;
import com.crm.graduation.crmsystem.dao.mapper.user.CrmUserMapper;
import com.crm.graduation.crmsystem.dao.mapper.user.CrmUserRoleMapper;
import com.crm.graduation.crmsystem.entity.dict.CrmDataDict;
import com.crm.graduation.crmsystem.entity.system.user.CrmUser;
import com.crm.graduation.crmsystem.entity.system.user.CrmUserRole;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import com.crm.graduation.crmsystem.model.vo.system.user.SelectUserVo;
import com.crm.graduation.crmsystem.model.vo.system.user.UserVo;
import com.crm.graduation.crmsystem.utils.Tools;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户service
 */
@Service
public class CrmUserService {

    @Resource
    private DaoSupport dao;

    @Resource
    private CrmUserMapper userMapper;

    @Resource
    private CrmDictMapper crmDictMapper;

    @Resource
    private CrmUserRoleMapper crmUserRoleMapper;

    /**
     * 根据用户名查询用户
     */
    public CrmUser getUserByUserName(String userName){
        CrmUser user = new CrmUser();
        user.setUserName(userName);
        user.setIsDelete("0");
        CrmUser user1 = userMapper.selectOne(user);
        return user1;
    }

    /**
     * 根据用户id来查询用户
     */
    public CrmUser getUserByUserId(String userId) throws Exception{
        CrmUser crmUser = userMapper.selectByPrimaryKey(userId);
        return crmUser;
    }

    /**
     * 更新用户信息
     */
    public boolean updataUserMessage(CrmUser user)throws Exception{
        boolean b = true;
        Date currentTime = new Date();
        user.setUpdataTime(currentTime);
        int i = userMapper.updateByPrimaryKeySelective(user);
        if(i<1){
            b = false;
        }
        return b;
    }

    /**
     * 修改密码
     */
    public boolean updataPassword(CrmUser user)throws Exception{
        boolean b = true;
        int i = userMapper.updateByPrimaryKeySelective(user);
        if(i<1){
            b = false;
        }
        return b;
    }

    /**
     * 用户首页列表
     */
    public List<UserVo> getUserList(SelectUserVo userVo)throws Exception{
        List<UserVo> userVos = null;
        List<CrmUser> users = (List<CrmUser>) dao.getList("CrmUserMapper.getUsers", userVo);
        if(users.size()>0){
            userVos = new ArrayList<>();
            for(CrmUser crmUser : users){
                UserVo userVo1 = new UserVo();
                BeanUtils.copyProperties(crmUser,userVo1);
                String userTypeOfWork = userVo1.getUserTypeOfWork();
                CrmDataDict crmDataDict = new CrmDataDict();
                crmDataDict.setDictCode(Consts.DICT_STAFF_TYPE_CODE);
                CrmDataDict crmDataDict1 = crmDictMapper.selectOne(crmDataDict);
                String dictId = crmDataDict1.getDictId();
                CrmDataDict dataDict = new CrmDataDict();
                dataDict.setParentId(dictId);
                dataDict.setDictCode(userTypeOfWork);
                CrmDataDict crmDataDict2 = crmDictMapper.selectOne(dataDict);
                if(crmDataDict2!=null){
                    String dictName = crmDataDict2.getDictName();
                    userVo1.setUserTypeOfWork(dictName);
                }else{
                    userVo1.setUserTypeOfWork("");
                }
                userVos.add(userVo1);
            }
        }
        return userVos;
    }

    /**
     * 删除用户
     */
    public String deleteUser(String userIds) throws Exception{
        String message = "fail";
        String[] split = userIds.split(",");
        for(String userId:split){
            CrmUser crmUser = userMapper.selectByPrimaryKey(userId);
            if(crmUser != null){
                crmUser.setIsDelete("1");
                userMapper.updateByPrimaryKey(crmUser);
                message = "success";
            }
        }
        return message;
    }

    /**
     * 新增用户
     */
    public String addUser(UserVo userVo)throws Exception{
        String message = "fail";
        if(userVo!=null){
            CrmUser crmUser = new CrmUser();
            BeanUtils.copyProperties(userVo,crmUser);
            crmUser.setIsDelete("0");
            crmUser.setUserId(Tools.get32UUID());
            crmUser.setCreateTime(new Date());
            //用户工号 STAFF+部门号+职位号+0000
            String userCode = "STAFF";
            String userDepartment = crmUser.getUserDepartment();
            String userTypeOfWork = crmUser.getUserTypeOfWork();
            userCode+=userDepartment;
            userCode+=userTypeOfWork;
            //查询用户数量
            int count = userMapper.selectCount(new CrmUser());
            count++;
            String strCount = (new DecimalFormat("0000")).format(count);
            userCode+=strCount;
            crmUser.setUserCode(userCode);
            //用户的密码加密，默认为1登录后可自行修改
            String password = Tools.passwordEncry("1", crmUser.getUserName());
            crmUser.setUserPassword(password);
            crmUser.setUserGold("0");
            userMapper.insert(crmUser);
            //处理权限角色
            CrmDataDict crmDataDict = new CrmDataDict();
            crmDataDict.setDictCode(Consts.DICT_STAFF_TYPE_CODE);
            CrmDataDict crmDataDict1 = crmDictMapper.selectOne(crmDataDict);
            if(crmDataDict1!=null){
                String dictId = crmDataDict1.getDictId();
                CrmDataDict crmDataDict2 = new CrmDataDict();
                crmDataDict2.setParentId(dictId);
                crmDataDict2.setDictCode(userTypeOfWork);
                CrmDataDict crmDataDict3 = crmDictMapper.selectOne(crmDataDict2);
                String roleId = crmDataDict3.getDictId();
                //添加角色用户中间
                CrmUserRole crmUserRole = new CrmUserRole();
                crmUserRole.setUserId(crmUser.getUserId());
                crmUserRole.setRoleId(roleId);
                crmUserRole.setUserRoleId(Tools.get32UUID());
                crmUserRoleMapper.insert(crmUserRole);
                message = "success";
            }
        }
        return message;
    }

    /**
     * 查询一个用户
     */
    public UserVo getUser(String userId)throws Exception{
        UserVo userVo = null;
        CrmUser crmUser = userMapper.selectByPrimaryKey(userId);
        if(crmUser!=null){
            userVo = new UserVo();
            BeanUtils.copyProperties(crmUser,userVo);
        }
        return userVo;
    }

    /**
     * 修改用户
     */
    public String editUser(UserVo userVo) throws Exception{
        String message = "fail";
        if(userVo!=null){
            CrmUser crmUser = userMapper.selectByPrimaryKey(userVo);
            if(crmUser!=null){
                crmUser.setEmail(userVo.getEmail());
                crmUser.setPhone(userVo.getPhone());
                crmUser.setUpdataTime(new Date());
                crmUser.setUserName(userVo.getUserName());
                crmUser.setUserDepartment(userVo.getUserDepartment());
                crmUser.setUserRealName(userVo.getUserRealName());
                crmUser.setUserTypeOfWork(userVo.getUserTypeOfWork());
                crmUser.setUserDescribe(userVo.getUserDescribe());
                userMapper.updateByPrimaryKey(crmUser);
                //修改权限
                CrmDataDict crmDataDict = new CrmDataDict();
                crmDataDict.setDictCode(Consts.DICT_STAFF_TYPE_CODE);
                CrmDataDict crmDataDict1 = crmDictMapper.selectOne(crmDataDict);
                String dictId = crmDataDict1.getDictId();
                CrmDataDict dataDict = new CrmDataDict();
                dataDict.setParentId(dictId);
                dataDict.setDictCode(userVo.getUserTypeOfWork());
                CrmDataDict crmDataDict2 = crmDictMapper.selectOne(dataDict);
                String roleId = crmDataDict2.getDictId();
                String userId = crmUser.getUserId();
                CrmUserRole crmUserRole = new CrmUserRole();
                crmUserRole.setUserId(userId);
                CrmUserRole crmUserRole1 = crmUserRoleMapper.selectOne(crmUserRole);
                if(crmUserRole1!=null){
                    crmUserRole1.setRoleId(roleId);
                    crmUserRoleMapper.updateByPrimaryKey(crmUserRole1);
                }
                message = "success";
            }
        }
        return message;
    }

}
