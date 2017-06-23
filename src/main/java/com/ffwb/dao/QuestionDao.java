package com.ffwb.dao;

import com.ffwb.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jinchuyang on 2017/6/23.
 */
public interface QuestionDao extends JpaRepository<Question, Long> {
}
