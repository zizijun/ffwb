package com.ffwb.DTO;

import com.ffwb.entity.Exam;

import java.util.List;

/**
 * Created by dearlhd on 2017/7/18.
 */
public class PaperDTO {
    private Exam exam;
    private List<QuestionDTO> questions;

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
