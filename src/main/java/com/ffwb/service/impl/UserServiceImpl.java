package com.ffwb.service.impl;

import com.ffwb.dao.UserDao;
import com.ffwb.entity.User;
import com.ffwb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jinchuyang on 2017/6/20.
 */
@Service("UserService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    /**
     * 增加用户
     * @param user
     * @return
     */
    @Override
    public User addUser(User user) {
        User newUser = new User();
        newUser.setId(-1l);
        String tel = user.getTel();
        if (userDao.findByTelAndAlive(tel, 1)!=null){
            return newUser;
        }
        user.setAlive(1);
        return userDao.save(user);
    }

    @Override
    public User login(String tel) {
        User user = userDao.findByTelAndAlive(tel, 1);
        return user;
    }
}
