package com.ffwb.service.impl;

import com.ffwb.dao.AnswerDao;
import com.ffwb.dao.ExamDao;
import com.ffwb.entity.Answer;
import com.ffwb.entity.Exam;
import com.ffwb.service.AnswerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by dearlhd on 2017/7/3.
 */
@Service("AnswerSerivce")
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    AnswerDao answerDao;

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public Answer addAnswer (Answer answer) {
        Answer temp = answerDao.findByExamAndQuestionAndAlive(answer.getExam(), answer.getQuestion(), answer.getAlive());
        if (temp != null) {
            return null;
        }
        return answerDao.save(answer);
    }

    @Override
    public Answer updateAnswer (Answer answer) {
        Answer temp = answerDao.findByExamAndQuestionAndAlive(answer.getExam(), answer.getQuestion(), answer.getAlive());
        if (temp == null) {
            return null;
        }
        temp.setAnswer(answer.getAnswer());
        return answerDao.save(temp);
    }

    @Override
    public boolean deleteAnswer(Answer answer) {
        answer.setAlive(0);
        if (updateAnswer(answer) == null) {
            return false;
        }
        return true;
    }

    @Override
    public List<Answer> getAnswersByExam (Exam exam) {
        List<Answer> answers = answerDao.findByExamAndAlive(exam, 1);
        return answers;
    }
}
