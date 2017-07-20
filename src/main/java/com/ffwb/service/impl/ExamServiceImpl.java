package com.ffwb.service.impl;

import com.ffwb.dao.AnswerDao;
import com.ffwb.dao.ExamDao;
import com.ffwb.dao.UserDao;
import com.ffwb.entity.*;
import com.ffwb.service.AnswerService;
import com.ffwb.service.ExamService;
import com.ffwb.service.TagService;
import com.ffwb.utils.GeneticAlgorithm.ExamRule;
import com.ffwb.utils.GeneticAlgorithm.GeneticAlgorithm;
import com.ffwb.utils.GeneticAlgorithm.Paper;
import com.ffwb.utils.GeneticAlgorithm.Population;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by dearlhd on 2017/7/3.
 */
@Service("ExamService")
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamDao examDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private TagService tagService;

    @Autowired
    private Population population;

    @Autowired
    private AnswerService answerService;

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public Exam createExam(Exam exam) {
        Exam newExam = new Exam();
        newExam.setId(-1);

        User user = userDao.findByIdAndAlive(exam.getUser().getId(), 1);
        if (user == null) {
            return newExam;
        }
        exam.setUser(user);
        if (examDao.findByUserAndName(exam.getUser(), exam.getName()) != null) {
            return newExam;
        }

        exam.setAlive(1);
        return examDao.save(exam);
    }

    @Override
    public Exam updateExam(Exam exam) {
        return examDao.save(exam);
    }

    @Override
    public Exam finishExam(Exam exam) throws ParseException {
        int costTime = exam.getCostTime();
        exam = examDao.findByIdAndAlive(exam.getId(), 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        Date d = df.parse(date);
        exam.setEndTime(d);
        exam.setCostTime(costTime);
        exam = examDao.save(exam);
        return exam;
    }

    @Override
    public Exam checkAnswers(Exam exam) throws IOException {
        List<Answer> answerList = answerDao.findByExamAndAlive(exam, 1);
        int totalPoint = 0;
        for (Answer answer : answerList){
            totalPoint+=answerService.judgeAnswers(answer);
        }
        exam.setGrade(totalPoint);
        exam = examDao.save(exam);
        return exam;
    }

    @Override
    // TODO 组卷，目前提供一种测试的组卷内容
    public List<Question> formPaper () {
        List<Tag> tags = tagService.findAllAlive(1);

        ExamRule rule = new ExamRule(10, 3.0, 5, 0, 0, 0, tags);

        // 迭代计数器
        int count = 0;
        // 迭代总次数
        int totalCount = 100;
        // 适应度期望值
        double expectation = 0.98;

        // 按照条件使用遗传算法组卷
        population.init(5, rule);

        logger.info("初次适应度: " + population.getFittest().getFitness());

        for (; count < totalCount; count++) {
            if (population.getFittest().getFitness() >= expectation) {
                break;
            }
            population.setPapers(GeneticAlgorithm.evolvePopulation(population, rule));
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
        return examDao.findByUserAndAlive(user, 1);
    }
}
