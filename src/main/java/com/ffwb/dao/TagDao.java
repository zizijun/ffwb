package com.ffwb.dao;

import com.ffwb.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by jinchuyang on 2017/7/12.
 */
public interface TagDao extends JpaRepository<Tag, Long> {
    List<Tag> findByAlive(int alive);

    Tag findByIdAndAlive(long id, int alive);

    Tag findByContentAndAlive(String content,int alive);
}
