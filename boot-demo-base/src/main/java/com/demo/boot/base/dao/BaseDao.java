package com.demo.boot.base.dao;

import java.util.List;

/**
 * Dao接口基类，业务Dao继承BaseDao就可获得一组已实现sqlMap了的公共操作方法
 *
 * @Author wude
 * @Create 2017-06-12 13:40
 */
public interface BaseDao<T, ID> {

    /**
     * 插入一条新记录
     *
     * @param t 记录
     * @return
     */
    int insert(T t);

    /**
     * 批量插入
     *
     * @param ts 记录列表
     * @return
     */
    int insertList(List<T> ts);

    /**
     * 根据ID删除记录
     *
     * @param id 主键
     * @return
     */
    int deleteById(ID id);

    /**
     * 根据主键查询记录
     *
     * @param id 主键
     * @return
     */
    T selectById(ID id);

    /**
     * 根据Model类查询一条记录
     *
     * @param t Model类
     * @return
     */
    T selectOne(T t);

    /**
     * 根据Model类查询记录
     *
     * @param t Model类
     * @return
     */
    List<T> selectList(T t);

    /**
     * 无条件全表查询
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 根据Model类统计记录数
     *
     * @param t Model类
     * @return
     */
    long count(T t);

    /**
     * 根据主键更新记录
     *
     * @param t Model类，须设置主键
     * @return
     */
    int update(T t);

}
