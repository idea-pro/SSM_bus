package com.firework.service;

import com.firework.bean.Route;

import java.util.List;

/**
 * 业务层-操控线路信息
 */
public interface RouteService {

    //根据片区与线路名查询指定/全部线路信息列表
    List<Route> selectList(Route route);

    //查询所有线路信息列表(用于在司机管理页面中获取线路信息)
    List<Route> selectAll();

    //添加线路信息
    int insert(Route route);

    //根据id删除指定线路信息
    int deleteById(Integer[] ids);

    //根据线路名获取指定线路信息
    Route findByName(String name);

    //根据id修改指定线路信息
    int update(Route route);

}
