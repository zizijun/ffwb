package com.ffwb.service;

import com.ffwb.entity.User;

/**
 * Created by jinchuyang on 2017/6/20.
 */
public interface UserService {
    User addUser(User user);

    User login(String tel);
}
