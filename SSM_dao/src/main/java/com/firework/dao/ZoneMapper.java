package com.firework.dao;

import com.firework.bean.Zone;

import java.util.List;

/**
 * 数据访问层-操控片区信息
 */
public interface ZoneMapper {

    //根据片区名称查询指定/全部片区信息列表
    List<Zone> selectList(Zone zonename);

    //查询所有片区信息列表(用于在线路管理页面中获取片区信息)
    List<Zone> selectAll();

    //根据片区名称查询指定的片区信息
    Zone findByName(String zonename);

    //添加片区信息
    int insert(Zone zone);

    //根据id修改指定片区信息
    int update(Zone zone);

    //根据id删除指定片区信息
    int deleteById(Integer[] ids);
}
