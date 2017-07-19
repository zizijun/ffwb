package com.ffwb.service;

import com.ffwb.entity.Answer;
import com.ffwb.entity.Exam;

import java.util.List;

/**
 * Created by dearlhd on 2017/7/3.
 */
public interface AnswerService {
    Answer addAnswer (Answer answer);
    Answer updateAnswer (Answer answer);

    int updateAnswers(List<Answer> answer);
    boolean deleteAnswer(Answer answer);

    List<Answer> getAnswersByExam (Exam exam);
}
