package com.mes.test;

import com.mes.pojo.User;
import com.mes.service.UserService;
import com.mes.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    // 创建接口对象
    UserService userService = new UserServiceImpl();
    @org.junit.jupiter.api.Test
    void registerUser() {
        userService.registerUser(new User(null,"12345","qwer","123@123456.com"));
        userService.registerUser(new User(null,"abc45","qwer","abc@123456.com"));
    }

    @org.junit.jupiter.api.Test
    void login() {
        System.out.println(userService.login(new User(null,"12345","qwer",null)));
    }

    @org.junit.jupiter.api.Test
    void existsUsername() {
        if(userService.existsUsername("12345")){
            System.out.println("用户名已经存在");
        }else {
            System.out.println("用户名可用！");
        }
    }
}