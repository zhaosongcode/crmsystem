package com.crm.graduation.crmsystem.config.shiro;

import com.crm.graduation.crmsystem.entity.system.permission.CrmPermission;
import com.crm.graduation.crmsystem.entity.system.role.CrmRole;
import com.crm.graduation.crmsystem.entity.system.user.CrmUser;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import com.crm.graduation.crmsystem.service.system.permission.CrmPermissionService;
import com.crm.graduation.crmsystem.service.system.role.CrmRoleService;
import com.crm.graduation.crmsystem.service.system.user.CrmUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private CrmRoleService roleService;

    @Resource
    private CrmPermissionService permissionService;

    @Resource
    private CrmUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    /**
     * 授权
     * 授权用户权限
     * 授权的方法是在碰到<shiro:hasPermission name=''></shiro:hasPermission>标签的时候调用的
     * 它会去检测shiro框架中的权限(这里的permissions)是否包含有该标签的name值,如果有,里面的内容显示
     * 如果没有,里面的内容不予显示(这就完成了对于权限的认证.)
     * 在这个方法中主要是使用类：SimpleAuthorizationInfo 进行角色的添加和权限的添加。
     *      * authorizationInfo.addRole(role.getRole()); authorizationInfo.addStringPermission(p.getPermission());
     *      *
     *      * 当然也可以添加set集合：roles是从数据库查询的当前用户的角色，stringPermissions是从数据库查询的当前用户对应的权限
     *      * authorizationInfo.setRoles(roles); authorizationInfo.setStringPermissions(stringPermissions);
     *      *
     *      * 就是说如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "perms[权限添加]");
     *      * 就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问
     *      *
     *      * 如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "roles[100002]，perms[权限添加]");
     *      * 就说明访问/add这个链接必须要有 "权限添加" 这个权限和具有 "100002" 这个角色才可以访问
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("开始查询授权");

        CrmUser user = (CrmUser) SecurityUtils.getSubject().getPrincipal();

        Session session = SecurityUtils.getSubject().getSession();

        //获取用户角色
        List<CrmRole> roles = null;
        try {
            roles = roleService.getListRoleByUserId(user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //添加角色
        SimpleAuthorizationInfo authorizationInfo =  new SimpleAuthorizationInfo();
        for (CrmRole role : roles) {
            authorizationInfo.addRole(role.getRoleName());
        }

        //获取用户权限
        Set<CrmPermission> permissions = null;
        try {
            permissions = permissionService.getListPermissionByRoleId(roles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //添加权限
        for (CrmPermission permission:permissions) {
            authorizationInfo.addStringPermission(permission.getPermissionUrl());
        }

        logger.info("查询授权 - SUCCESS");

        return authorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("开始认证");
        //获取用户的输入的账号.第一种方法
        //String username = (String)authenticationToken.getPrincipal();
        //获取用户名 密码 第二种方式
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        //从数据库查询出用户
        CrmUser user = null;
        try {
            user = userService.getUserByUserName(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user==null) {
            throw new UnknownAccountException();//没有账号
        }
        /*if (0==user.getEnable()) {
            throw new LockedAccountException(); // 帐号锁定
        }*/
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户
                user.getUserPassword(), //密码
                //ByteSource.Util.bytes(username),//加盐
                getName()  //realm name
        );
        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(Consts.SESSION_USER, user);
        logger.info("认证成功");
        return authenticationInfo;
    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 更新用户信息缓存.
     */
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    /**
     * 清除用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 清除用户信息缓存.
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 清空所有缓存
     */
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }


    /**
     * 清空所有认证缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
