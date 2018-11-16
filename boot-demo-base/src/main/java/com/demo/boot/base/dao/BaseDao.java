package com.demo.boot.base.dao;

import java.util.List;

/**
 * 业务DAO接口类的基类, 提供一组共通方法
 *
 * @Author wude
 * @Create 2017-06-12 13:40
 */
public interface BaseDao<T, ID> {

    int insert(T t);

    int insertList(List<T> ts);

    int deleteById(ID id);

    T selectById(ID id);

    T selectOne(T t);

    List<T> selectList(T t);

    List<T> selectAll();

    int update(T t);

}
