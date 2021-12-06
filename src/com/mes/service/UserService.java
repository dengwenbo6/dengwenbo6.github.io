package com.mes.service;

import com.mes.pojo.User;

public interface UserService {

    /**
     * 注册用户
     * @param user
     */
    public void registerUser(User user);

    /**
     * 登录业务
     * @param user
     * @return  如果返回null说明登录失败，返回有值说明登陆成功，
     */
    public User login(User user);

    /**
     * 检查用户是否可用
     * @param username
     * @return  返回true表示用户已经存在，返回false表示用户可用
     */
    public boolean existsUsername(String username);





}
