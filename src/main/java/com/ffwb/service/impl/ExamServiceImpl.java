package com.ffwb.service.impl;

import com.ffwb.dao.ExamDao;
import com.ffwb.entity.Exam;
import com.ffwb.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by dearlhd on 2017/7/3.
 */
@Service("ExamService")
public class ExamServiceImpl implements ExamService {
    @Autowired
    ExamDao examDao;

    @Override
    public Exam createExam(Exam exam) {
        Exam newExam = new Exam();
        newExam.setId(-1L);
        if (examDao.findByUserIdAndName(exam.getUserId(), exam.getName()) != null) {
            return newExam;
        }
        exam.setTotalTime(-1);
        exam.setCostTime(-1);
        exam.setGrade(-1);
        exam.setTotalScore(-1);
        exam.setAlive(1);
        return examDao.save(exam);
    }
}
