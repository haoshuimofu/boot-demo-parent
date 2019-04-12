package com.demo.boot.base.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 带分表符的Dao基类
 *
 * @Author wude
 * @Create 2019-04-12 10:39
 */
public interface BasePartitionDao<T, ID> {

    /**
     * 插入一条新记录
     *
     * @param t 记录
     * @return
     */
    int insert(T t, @Param("partition") String partition);

    /**
     * 批量插入
     *
     * @param ts 记录列表
     * @return
     */
    int insertList(List<T> ts, @Param("partition") String partition);

    /**
     * 根据ID删除记录
     *
     * @param id 主键
     * @return
     */
    int deleteById(ID id, @Param("partition") String partition);

    /**
     * 根据主键查询记录
     *
     * @param id 主键
     * @return
     */
    T selectById(ID id, @Param("partition") String partition);

    /**
     * 根据Model类查询一条记录
     *
     * @param t Model类
     * @return
     */
    T selectOne(T t, @Param("partition") String partition);

    /**
     * 根据Model类查询记录
     *
     * @param t Model类
     * @return
     */
    List<T> selectList(T t, @Param("partition") String partition);

    /**
     * 无条件全表查询
     *
     * @return
     */
    List<T> selectAll(@Param("partition") String partition);

    /**
     * 根据Model类统计记录数
     *
     * @param t Model类
     * @return
     */
    long count(T t, @Param("partition") String partition);

    /**
     * 根据主键更新记录
     *
     * @param t Model类，须设置主键
     * @return
     */
    int update(T t, @Param("partition") String partition);

}