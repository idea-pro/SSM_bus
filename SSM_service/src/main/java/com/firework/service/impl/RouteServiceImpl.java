package com.firework.service.impl;

import com.firework.bean.Route;
import com.firework.dao.RouteMapper;
import com.firework.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *业务层实现类-操控线路信息
 */
@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    //注入Mapper接口对象
    @Autowired
    private RouteMapper routeMapper;

    @Override
    public List<Route> selectList(Route route) { return routeMapper.selectList(route); }

    @Override
    public List<Route> selectAll() {
        return routeMapper.selectAll();
    }

    @Override
    public Route findByName(String name) {
        return routeMapper.findByName(name);
    }

    @Override
    public int insert(Route route) {
        return routeMapper.insert(route);
    }

    @Override
    public int update(Route route) {
        return routeMapper.update(route);
    }

    @Override
    public int deleteById(Integer[] ids) {
        return routeMapper.deleteById(ids);
    }
}
