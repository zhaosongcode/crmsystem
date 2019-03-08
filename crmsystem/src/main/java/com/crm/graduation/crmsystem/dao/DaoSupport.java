package com.crm.graduation.crmsystem.dao;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * dao层实现
 */
@Repository("daoSupport")
public class DaoSupport implements Dao {

    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 保存对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Object save(String str, Object obj) throws Exception {
        return sqlSessionTemplate.insert(str,obj);
    }

    /**
     * 批量保存
     * @param str
     * @param objs
     * @return
     * @throws Exception
     */
    public Object batchSave(String str, List objs )throws Exception{
        return sqlSessionTemplate.insert(str, objs);
    }

    /**
     * 批量更新
     * @param str
     * @param objs
     * @return
     * @throws Exception
     */
    public void batchUpdate(String str, List objs )throws Exception{
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        //批量执行器
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        try{
            if(objs!=null){
                for(int i=0,size=objs.size();i<size;i++){
                    sqlSession.update(str, objs.get(i));
                }
                sqlSession.flushStatements();
                sqlSession.commit();
                sqlSession.clearCache();
            }
        }finally{
            sqlSession.close();
        }
    }

    /**
     * 更新对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Object update(String str, Object obj) throws Exception {
        return sqlSessionTemplate.update(str,obj);
    }

    /**
     * 删除对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Object delete(String str, Object obj) throws Exception {
        return sqlSessionTemplate.delete(str,obj);
    }

    /**
     * 批量删除
     * @param str
     * @param objs
     * @return
     * @throws Exception
     */
    public Object batchDelete(String str, List objs )throws Exception{
        return sqlSessionTemplate.delete(str, objs);
    }

    /**
     * 获取一个对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Object getObject(String str, Object obj) throws Exception {
        return sqlSessionTemplate.selectOne(str, obj);
    }

    /**
     * 获取对象集合
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public Object getList(String str, Object obj) throws Exception {
        return sqlSessionTemplate.selectList(str, obj);
    }

    /**
     * 获取对象map
     * @param sql
     * @param obj
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    @Override
    public Object getMap(String sql, Object obj, String key, String value) throws Exception {
        return sqlSessionTemplate.selectMap(sql, obj, key);
    }
}
