package com.ffwb.service;

import com.ffwb.entity.Answer;
import com.ffwb.entity.Exam;
import com.ffwb.entity.Question;

import java.util.List;

/**
 * Created by dearlhd on 2017/7/3.
 */
public interface ExamService {
    Exam createExam (Exam exam);
    List<Question> formPaper ();
}
