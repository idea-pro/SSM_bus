package com.firework.service;

import com.firework.bean.Zone;

import java.util.List;

/**
 * 业务层-操控片区信息
 */
public interface ZoneService {

    //根据片区名称查询指定/全部片区列表信息
    List<Zone> selectList(Zone gradename);

    //查询所有片区列表信息(用于在线路管理页面中获取片区信息)
    List<Zone> selectAll();

    //根据片区名称查询指定片区信息
    Zone findByName(String gradename);

    //添加片区信息
    int insert(Zone zone);

    //根据id修改指定片区信息
    int update(Zone zone);

    //根据id删除指定片区信息
    int deleteById(Integer[] ids);
}
