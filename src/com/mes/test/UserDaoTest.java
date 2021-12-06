package com.mes.test;

import com.mes.dao.UserDao;
import com.mes.dao.impl.UserDaoImpl;
import com.mes.pojo.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoTest {
    UserDao userDao = new UserDaoImpl();

    /**
     * 查询用户是否存在
     */
    @Test
    public void queryUserByUsername() {
        if(userDao.queryUserByUsername("admin")==null){
            System.out.println("用户名可用");
        }else {
            System.out.println("用户名已存在");
        };
    }

    /**
     * 查询用户名和密码是否正确测试语句
     */
    @Test
    public void queryUserByUsernameAndPassword() {
        if (userDao.queryUserByUsernameAndPassword("admin","admin") == null) {
            System.out.println("用户名或密码错误，登录失败！");
        } else {
            System.out.println("查询成功！");
        }
    }
    /**
     * update数据
     */
    @Test
    public void saveUser() {
        System.out.println(userDao.SaveUser( new User(null,"root","123","123@123.com")));
    }
}