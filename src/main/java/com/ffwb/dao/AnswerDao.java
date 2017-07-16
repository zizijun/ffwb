package com.ffwb.dao;

import com.ffwb.entity.Answer;
import com.ffwb.entity.Exam;
import com.ffwb.entity.Question;
import com.ffwb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by jinchuyang on 2017/7/14.
 */
public interface AnswerDao extends JpaRepository<Answer, Long>{
    List<Answer> findByUserAndAlive(User user, int live);
    Answer findByExamAndQuestionAndAlive (Exam exam, Question question, int alive);
}
