package com.ffwb.service.impl;

import com.ffwb.dao.ExamDao;
import com.ffwb.entity.Answer;
import com.ffwb.entity.Exam;
import com.ffwb.entity.Question;
import com.ffwb.entity.User;
import com.ffwb.service.ExamService;
import com.ffwb.utils.GeneticAlgorithm.ExamRule;
import com.ffwb.utils.GeneticAlgorithm.GeneticAlgorithm;
import com.ffwb.utils.GeneticAlgorithm.Paper;
import com.ffwb.utils.GeneticAlgorithm.Population;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by dearlhd on 2017/7/3.
 */
@Service("ExamService")
public class ExamServiceImpl implements ExamService {
    @Autowired
    ExamDao examDao;

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public Exam createExam(Exam exam) {
        Exam newExam = new Exam();
        newExam.setId(-1L);
        if (examDao.findByUserAndName(exam.getUser(), exam.getName()) != null) {
            return newExam;
        }
        exam.setTotalTime(-1);
        exam.setCostTime(-1);
        exam.setGrade(-1);
        exam.setTotalScore(-1);
        exam.setAlive(1);
        return examDao.save(exam);
    }

    @Override
    // TODO 组卷，目前提供一种测试的组卷内容
    public List<Question> formPaper () {
        ExamRule rule = new ExamRule(20, 3.0, 10, 0, 0, 0);

        // 迭代计数器
        int count = 0;
        // 迭代总次数
        int totalCount = 100;
        // 适应度期望值
        double expectation = 0.98;

        // 按照条件使用遗传算法组卷
        Population population = new Population(20, rule);

        logger.info("初次适应度: " + population.getFittest().getFitness());

        for (; count < totalCount; count++) {
            if (population.getFittest().getFitness() >= expectation) {
                break;
            }
            population = GeneticAlgorithm.evolvePopulation(population, rule);
            logger.info("第" + count + "次进化后，适应度为： " + population.getFittest().getFitness());
        }
        Paper paper = population.getFittest();
        return paper.getQuestions();
    }

    @Override
    public Exam findExamById (long examId) {
        return examDao.findByIdAndAlive(examId, 1);
    }

    @Override
    public List<Exam> getExamsByUser (User user) {
        return examDao.findByUserAAndAlive(user, 1);
    }
}
