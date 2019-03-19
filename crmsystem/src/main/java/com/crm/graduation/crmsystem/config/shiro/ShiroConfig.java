package com.crm.graduation.crmsystem.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.crm.graduation.crmsystem.property.RedisProperty;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import redis.clients.jedis.JedisPoolConfig;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * shiro配置类
 */
@Configuration
@EnableConfigurationProperties(RedisProperty.class)
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * shiro的过滤器
     * @param securityManager
     * @return
     */
    @Bean(name = "shirFilter")
    public ShiroFilterFactoryBean shirFilter(@Qualifier("securityManager") SecurityManager securityManager) throws Exception {
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/main/index");
        //未授权界面;
       shiroFilterFactoryBean.setUnauthorizedUrl("/main/index");
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        filterChainDefinitionMap.put("/favicon.ico","anon");
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/login_login","anon");
        filterChainDefinitionMap.put("/logout","logout");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/images/**","anon");
        filterChainDefinitionMap.put("/code/generate","anon");
        filterChainDefinitionMap.put("/endpointChat/**","anon");
        filterChainDefinitionMap.put("/uploadImages/**","anon");
        filterChainDefinitionMap.put("/upload/**","anon");
        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置核心安全事务管理
     * @return
     */
    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("sessionManager") SessionManager sessionManager,
                                           @Qualifier("cacheManager") CacheManager cacheManager){
        DefaultSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(shiroRealm());
        manager.setCacheManager(cacheManager);
        // 自定义session管理 使用redis
        manager.setSessionManager(sessionManager);
        return  manager;
    }

    /**
     * 配置Shiro生命周期处理器
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     *  身份认证realm; (这个需要自己写，账号密码校验；权限等)
     * @return
     */
    @Bean
    public MyShiroRealm shiroRealm(){
        MyShiroRealm shiroRealm = new MyShiroRealm();
        //配置自定义密码比较器
        shiroRealm.setCredentialsMatcher(credentialsMatcher());
        return shiroRealm;
    }

    /**
     * 密码加密配置
     * @return
     */
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher(){
        return new CredentialsMatcher();
    }

    /**
     * 开启shiro 注解模式
     * 可以在controller中的方法前加上注解
     * 如 @RequiresPermissions("userInfo:add")
     * @param securityManager
     * @return
     */
    /*@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }*/

    /**
     * 解决： 无权限页面不跳转 shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized") 无效
     * shiro的源代码ShiroFilterFactoryBean.Java定义的filter必须满足filter instanceof AuthorizationFilter，
     * 只有perms，roles，ssl，rest，port才是属于AuthorizationFilter，而anon，authcBasic，auchc，user是AuthenticationFilter，
     * 所以unauthorizedUrl设置后页面不跳转 Shiro注解模式下，登录失败与没有权限都是通过抛出异常。
     * 并且默认并没有去处理或者捕获这些异常。在SpringMVC下需要配置捕获相应异常来通知用户信息
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver=new SimpleMappingExceptionResolver();
        Properties properties=new Properties();
        //这里的 /unauthorized 是页面，不是访问的路径
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException","/index");
        properties.setProperty("org.apache.shiro.authz.UnauthenticatedException","/index");
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        return simpleMappingExceptionResolver;
    }

    /**
     * 解决spring-boot Whitelabel Error Page
     * @return
     */
   /* @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {

                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/unauthorized.html");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

                container.addErrorPages(error401Page, error404Page, error500Page);
            }
        };
    }*/

    /**
     * cookie对象;会话Cookie模板 ,默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid或rememberMe，自定义
     * @return
     */
    /*@Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }*/

    /**
     * cookie管理对象;记住我功能,rememberMe管理器
     * @return
     */
   /* @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }
*/
    /**
     * FormAuthenticationFilter 过滤器 过滤记住我
     * @return
     */
    /*@Bean
    public FormAuthenticationFilter formAuthenticationFilter(){
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
        //对应前端的checkbox的name = rememberMe
        formAuthenticationFilter.setRememberMeParam("rememberMe");
        return formAuthenticationFilter;
    }*/

    /**
     * shiro缓存管理器;
     * 需要添加到securityManager中
     * @return
     */
    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager(@Qualifier("redisManager") RedisManager redisManager){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        //redis中针对不同用户缓存
        redisCacheManager.setPrincipalIdFieldName("userId");
        //用户权限信息缓存时间
        //redisCacheManager.setExpire(2000000);
        return redisCacheManager;
    }
    @Bean(name = "redisManager")
    public RedisManager redisManager(@Qualifier("jedisPoolConfig") JedisPoolConfig jedisPoolConfig, RedisProperty redisProperty){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisProperty.getHost());
        redisManager.setJedisPoolConfig(jedisPoolConfig);
        redisManager.setPort(redisProperty.getPort());
        redisManager.setTimeout(3000);
        redisManager.setPassword(redisProperty.getPassword());
        return redisManager;
    }

    /**
     * 配置会话管理器，设定会话超时及保存
     * @return
     */
    @Bean("sessionManager")
    public SessionManager sessionManager(@Qualifier("sessionDAO") SessionDAO sessionDAO,
                                         @Qualifier("sessionValidationScheduler") SessionValidationScheduler svs,
                                         @Qualifier("cookie") Cookie cookie) {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        //定义的是全局的session会话超时时间,单位毫秒
        defaultWebSessionManager.setGlobalSessionTimeout(1800000);

        //删除所有无效的Session对象，此时的session被保存在了内存里面
        defaultWebSessionManager.setDeleteInvalidSessions(true);

        //需要让此session可以使用该定时调度器进行检测
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        //定义要使用的无效的Session定时调度器
        defaultWebSessionManager.setSessionValidationScheduler(svs);

        //所有的session一定要将id设置到Cookie之中，需要提供有Cookie的操作模版
        defaultWebSessionManager.setSessionIdCookie(cookie);
        //定义sessionIdCookie模版可以进行操作的启用
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        //定义Session可以进行序列化的工具类
        defaultWebSessionManager.setSessionDAO(sessionDAO);
        return defaultWebSessionManager;
    }
    @Bean(name = "cookie")
    public Cookie cookie(){
        Cookie sessionIdCookie = new SimpleCookie();
        sessionIdCookie.setName("JSESSIONID");
        sessionIdCookie.setHttpOnly(true);
        sessionIdCookie.setMaxAge(-1);
        sessionIdCookie.setPath("/");
        return sessionIdCookie;
    }
    @Bean(name = "sessionValidationScheduler")
    public SessionValidationScheduler sessionValidationScheduler(){
        ExecutorServiceSessionValidationScheduler sessionValidationScheduler =  new ExecutorServiceSessionValidationScheduler();
        //设置session失效扫描时间，单位为毫秒
        sessionValidationScheduler.setInterval(180000);
        return sessionValidationScheduler;
    }
    @Bean(name = "sessionDAO")
    public SessionDAO sessionDAO(@Qualifier("redisManager") RedisManager redisManager,
                                 @Qualifier("sessionIdGenerator")SessionIdGenerator sessionIdGenerator) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setKeyPrefix("shiro-activeSessionCache");
        redisSessionDAO.setRedisManager(redisManager);
        //session在redis中的保存时间,最好大于session会话超时时间
        //redisSessionDAO.setExpire(2000000);
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator);
        return redisSessionDAO;
    }
    @Bean(name = "sessionIdGenerator")
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * 让某个实例的某个方法的返回值注入为Bean的实例
     * Spring静态注入
     * @return
     */
    /*@Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean(){
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{securityManager()});
        return factoryBean;
    }*/

    /**
     * 配置session监听
     * @return
     */
    /*@Bean("sessionListener")
    public ShiroSessionListener sessionListener(){
        ShiroSessionListener sessionListener = new ShiroSessionListener();
        return sessionListener;
    }*/

    /**
     * session 管理对象
     *
     * @return DefaultWebSessionManager
     */
    /*@Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置session超时时间，单位为毫秒
        sessionManager.setGlobalSessionTimeout(200000);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        // 网上各种说要自定义sessionDAO 其实完全不必要，shiro自己就自定义了一个，可以直接使用，还有其他的DAO，自行查看源码即可
        sessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        sessionManager.setCacheManager(cacheManager());
        return sessionManager;
    }*/

    /**
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     * 默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid
     * @return
     */
    /*@Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie(){

        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //设置名称
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }*/

    /**
     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * MemorySessionDAO 直接在内存中进行会话维护
     * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     * @return
     */



    /**
     * 配置会话ID生成器
     * @return
     */




    /**
     * 定时调度器
     * @return
     */


    /**
     * 并发登录控制
     * @return
     */
    /*@Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter(){
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
       //用于根据会话ID，获取会话进行踢出操作的；
       kickoutSessionControlFilter.setSessionManager(sessionManager());
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        kickoutSessionControlFilter.setCacheManager(cacheManager());
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
        kickoutSessionControlFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        kickoutSessionControlFilter.setMaxSession(1);
        //被踢出后重定向到的地址；
        kickoutSessionControlFilter.setKickoutUrl("/login?kickout=1");
        return kickoutSessionControlFilter;
    }*/

    /*@Bean
    public JedisPool jedisPool(){
        JedisPool jedisPool = new JedisPool("localhost",6379);
        return jedisPool;
    }

    *//**
     * rdeis管理
     * @return
     *//*
    @Bean(name = "redisManager")
    public RedisManager redisManager(@Qualifier("jedisPoolConfig") JedisPoolConfig jedisPoolConfig) {
        RedisManager redisManager = new RedisManager();
        redisManager.setJedisPoolConfig(jedisPoolConfig);
        redisManager.setJedisPool(jedisPool());
        return redisManager;
    }

    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager(@Qualifier("redisManager")RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    @Bean(name = "redisSessionDAO")
    public RedisSessionDAO redisSessionDAO(@Qualifier("redisManager") RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setKeyPrefix("shiro-activeSessionCache");
        redisSessionDAO.setRedisManager(redisManager);
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return redisSessionDAO;
    }

    *//**
     * 定时调度器
     * @return
     *//*
    @Bean
    public SessionValidationScheduler sessionValidationScheduler(){
        ExecutorServiceSessionValidationScheduler sessionValidationScheduler =  new ExecutorServiceSessionValidationScheduler();
        //设置session失效扫描时间，单位为毫秒
        sessionValidationScheduler.setInterval(180000);
        return sessionValidationScheduler;
    }

    @Bean(name = "cookie")
    public Cookie cookie(){
        Cookie sessionIdCookie = new SimpleCookie();
        sessionIdCookie.setName("JSESSIONID");
        sessionIdCookie.setHttpOnly(true);
        sessionIdCookie.setMaxAge(-1);
        return sessionIdCookie;
    }*/

    /*@Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager(@Qualifier("redisSessionDAO") RedisSessionDAO redisSessionDAO,
                                                   @Qualifier("cookie") Cookie cookie) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(180000);
        //删除所有无效的Session对象，此时的session被保存在了内存里面
        sessionManager.setDeleteInvalidSessions(true);

        //需要让此session可以使用该定时调度器进行检测
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //定义要使用的无效的Session定时调度器
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler());

        //所有的session一定要将id设置到Cookie之中，需要提供有Cookie的操作模版
        sessionManager.setSessionIdCookie(cookie);
        //定义sessionIdCookie模版可以进行操作的启用
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }*/

}
