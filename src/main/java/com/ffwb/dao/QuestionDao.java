package com.ffwb.dao;

import com.ffwb.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by jinchuyang on 2017/6/23.
 */
public interface QuestionDao extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {
    Page<Question> findByAlive(int alive, Pageable pageable);
}
