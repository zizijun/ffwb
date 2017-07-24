package com.ffwb.dao;

import com.ffwb.entity.Skill;
import com.ffwb.entity.Tag;
import com.ffwb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by jinchuyang on 2017/7/14.
 */
public interface SkillDao extends JpaRepository<Skill, Long>, JpaSpecificationExecutor<Skill> {
    Skill findByTagAndUserAndAlive(Tag tag, User user, int alive);
    List<Skill> findAllByAlive(int alive);
    List<Skill> findByUserAndAlive(User user, int alive);
    List<Skill> findByTagAndAlive(Tag tag, int alive);

}
