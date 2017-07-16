package com.ffwb.dao;

import com.ffwb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by jinchuyang on 2017/6/20.
 */
public interface UserDao extends JpaRepository<User, Long>{
    User findByTelAndAlive(String tel, int alive);
    User findByIdAndAlive (long id, int alive);
    List<User> findByAlive( int alive);
}
