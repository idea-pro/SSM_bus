package com.firework.service.impl;

import com.firework.bean.Zone;
import com.firework.dao.ZoneMapper;
import com.firework.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务层实现类-操控片区信息
 */
@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

    //注入Mapper接口对象
    @Autowired
    private ZoneMapper zoneMapper;

    @Override
    public List<Zone> selectList(Zone zonename) {
        return zoneMapper.selectList(zonename);
    }

    @Override
    public List<Zone> selectAll() {
        return zoneMapper.selectAll();
    }

    @Override
    public Zone findByName(String zonename) {
        return zoneMapper.findByName(zonename);
    }

    @Override
    public int insert(Zone zone) {
        return zoneMapper.insert(zone);
    }

    @Override
    public int update(Zone zone) {
        return zoneMapper.update(zone);
    }

    @Override
    public int deleteById(Integer[] ids) {
        return zoneMapper.deleteById(ids);
    }
}