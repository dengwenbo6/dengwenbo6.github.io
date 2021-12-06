package com.mes.dao;

import com.mes.pojo.User;

public interface UserDao {

    /**
     *根据用户查询用户信息
     * @param username  用户名
     * @return 如果返回null说明没有这个用户，反之亦然
     */
    public User queryUserByUsername(String username);

    /**
     * 根据有户名和密码查用户信息
     * @param username  用户名
     * @param password 用户密码
     * @return 如果返回null说明用户名和密码错误
     */
    public User queryUserByUsernameAndPassword(String username,String password);
    /**
     * 保存用户信息
     * @param user user
     * @return  返回-1表示保存失败，其他的表示影响的行数
     */
    public int SaveUser(User user);

}
