package com.ffwb.service;

import com.ffwb.entity.Exam;
import com.ffwb.entity.Question;
import com.ffwb.entity.User;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by dearlhd on 2017/7/3.
 */
public interface ExamService {
    Exam createExam (Exam exam);

    List<Question> formPaper ();

    Exam findExamById (long examId);

    List<Exam> getExamsByUser (User user);

    Exam updateExam (Exam exam);

    Exam finishExam(Exam exam) throws ParseException;

    Exam checkAnswers(Exam exam) throws IOException;
}
