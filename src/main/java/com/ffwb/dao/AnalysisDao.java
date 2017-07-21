package com.ffwb.dao;

import com.ffwb.entity.Analysis;
import com.ffwb.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by lenovo on 2017/7/15.
 */
public interface AnalysisDao extends JpaRepository<Analysis,Long> {
    public List<Analysis> findByQuestionAndAlive(Question question,int alive);
}
