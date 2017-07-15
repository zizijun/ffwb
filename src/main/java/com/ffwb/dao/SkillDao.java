package com.ffwb.dao;

import com.ffwb.entity.Skill;
import com.ffwb.entity.Tag;
import com.ffwb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jinchuyang on 2017/7/14.
 */
public interface SkillDao extends JpaRepository<Skill, Long>{
    Skill findByTagAndUserAndAlive(Tag tag, User user, int alive);
}
