package com.firework.service;

import com.firework.bean.Driver;
import com.firework.bean.LoginForm;

import java.util.List;

/**
 * 业务层-操控司机信息
 */
public interface DriverService {

    //验证登录信息是否正确
    Driver login(LoginForm loginForm);

    //根据线路与司机名获取指定或全部司机信息列表
    List<Driver> selectList(Driver driver);

    //根据车牌号获取指定司机信息
    Driver fingBySno(Driver driver);

    //添加线路信息
    int insert(Driver driver);

    //根据id修改指定司机信息
    int update(Driver driver);

    //根据id修改指定司机密码
    int updatePassowrd(Driver driver);

    //根据id删除指定司机信息
    int deleteById(Integer[] ids);

}

