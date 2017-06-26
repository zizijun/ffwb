package com.ffwb.dao;

import com.ffwb.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by jinchuyang on 2017/6/23.
 */
public interface QuestionDao extends JpaRepository<Question, Long> {
    Page<Question> findByAlive(int alive, Pageable pageable);
}
