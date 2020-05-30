package com.firework.dao;

import com.firework.bean.Route;

import java.util.List;

/**
 * 数据访问层-操控线路信息
 */
public interface RouteMapper {

    //根据线路名称获取指定/全部线路信息列表
    List<Route> selectList(Route route);

    //查询所有线路列表信息(用于在司机管理页面中获取线路信息)
    List<Route> selectAll();

    //获取指定名称的线路信息
    Route findByName(String name);

    //添加线路信息
    int insert(Route route);

    //根据id删除指定线路信息
    int deleteById(Integer[] ids);

    //根据id修改指定线路信息
    int update(Route route);

}
