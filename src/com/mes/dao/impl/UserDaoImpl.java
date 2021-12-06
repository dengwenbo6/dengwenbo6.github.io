package com.mes.dao.impl;

import com.mes.dao.UserDao;
import com.mes.pojo.User;

public class UserDaoImpl extends BaseDao implements UserDao {
    /**
     * 返回通过用户名查询的用户信息
     *
     * @param username 用户名
     * @return 查询到了返回这条信息，没有查询到就返回null
     */
    @Override
    public User queryUserByUsername(String username) {
        String sql = "select `id`,`username`,`password`,`email` from t_user where username=?";

        return queryForOne(User.class, sql, username);
    }

    /**
     * 通过用户名和密码查询用户信息
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 如果查询到就返回整条数据，如果没有查询到就返回null
     */
    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        String sql = "select `id`,`username`,`password`,`email` from t_user where username=? and password=?";

        return queryForOne(User.class, sql, username, password);
    }

    /**
     * 保存用户信息
     *
     * @param user 用户对象
     * @return 返回-1  表示操作失败，其他是sql语句影响的行数
     */
    @Override
    public int SaveUser(User user) {
        String sql = "insert into t_user(username,password,email) values(?,?,?)";
        return update(sql, user.getUsername(), user.getPassword(), user.getEmail());
    }
}
