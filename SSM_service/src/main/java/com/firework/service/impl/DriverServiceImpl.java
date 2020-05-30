package com.firework.service.impl;

import com.firework.bean.Driver;
import com.firework.bean.LoginForm;
import com.firework.dao.DriverMapper;
import com.firework.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务层实现类-操控司机信息
 */
@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    //注入Mapper接口对象
    @Autowired
    private DriverMapper driverMapper;

    @Override
    public Driver login(LoginForm loginForm) {
        return driverMapper.login(loginForm);
    }

    @Override
    public Driver fingBySno(Driver driver) {
        return driverMapper.findBySno(driver);
    }

    @Override
    public List<Driver> selectList(Driver driver) {
        return driverMapper.selectList(driver);
    }

    @Override
    public int insert(Driver driver) {
        return driverMapper.insert(driver);
    }

    @Override
    public int update(Driver driver) {
        return driverMapper.update(driver);
    }

    @Override
    public int updatePassowrd(Driver driver) {
        return driverMapper.updatePassword(driver);
    }

    @Override
    public int deleteById(Integer[] ids) {
        return driverMapper.deleteById(ids);
    }
}
