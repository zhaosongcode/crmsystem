package com.crm.graduation.crmsystem.dao;

/**
 * dao层
 */
public interface Dao {

    /**
     * 保存对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object save(String str, Object obj) throws Exception;

    /**
     * 修改对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object update(String str, Object obj) throws Exception;

    /**
     * 删除对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object delete(String str, Object obj) throws Exception;

    /**
     * 查找对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object getObject(String str, Object obj) throws Exception;

    /**
     * 查找对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object getList(String str, Object obj) throws Exception;

    /**
     * 查找对象封装成Map
     * @param sql
     * @param obj
     * @return
     * @throws Exception
     */
    public Object getMap(String sql, Object obj, String key, String value) throws Exception;
}
