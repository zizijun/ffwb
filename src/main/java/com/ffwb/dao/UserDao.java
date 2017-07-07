package com.ffwb.dao;

import com.ffwb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jinchuyang on 2017/6/20.
 */
public interface UserDao extends JpaRepository<User, Long>{
    User findByTelAndAlive(String tel, int i);
}
