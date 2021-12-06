package com.mes.service.impl;

import com.mes.dao.UserDao;
import com.mes.dao.impl.UserDaoImpl;
import com.mes.pojo.User;
import com.mes.service.UserService;

public class UserServiceImpl implements UserService {
    //  创建接口类的对象，在业务层调用实现业务功能
    private UserDao userDao = new UserDaoImpl();

    /**
     * 注册业务，需要保存用户信息
     * @param user
     */
    @Override
    public void registerUser(User user) {
        userDao.SaveUser(user);
    }

    /**
     * 登录业务，需要返回是否存在这个用户名和密码
     * @param user
     * @return  验证是否存在这个用户名和密码
     */
    @Override
    public User login(User user) {

        return userDao.queryUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {
        if(userDao.queryUserByUsername(username)==null){
            // 当等于null时说明没有查到，这时候说明可以添加用户
            return false;
        }
        return true;
    }
}
