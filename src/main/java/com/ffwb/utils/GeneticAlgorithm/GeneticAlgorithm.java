package com.ffwb.utils.GeneticAlgorithm;

import com.ffwb.entity.Question;
import com.ffwb.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dearlhd on 2017/7/11.
 */
@Component
public class GeneticAlgorithm {
    /**
     * 变异概率
     */
    private static final double mutationRate = 0.085;
    /**
     * 精英主义
     */
    private static final boolean elitism = true;
    /**
     * 淘汰数组大小
     */
    private static final int tournamentSize = 5;

    @Autowired
    private QuestionService qService;

    private static QuestionService questionService;

    @PostConstruct
    public void initService () {
        questionService = qService;
    }

    /**
     * TODO 进化种群
     */
    public static List<Paper> evolvePopulation(Population pop, ExamRule examRule) {
        List<Paper> papers = new ArrayList<>();
        int elitismOffset;
        // 精英主义
        if (elitism) {
            elitismOffset = 1;
            // 保留上一代最优秀个体
            Paper fittest = pop.getFittest();
            fittest.setId(0);
            papers.add(fittest);
        }
        // 种群交叉操作，从当前的种群pop来创建下一代种群
        for (int i = elitismOffset; i < pop.getLength(); i++) {
            // 较优选择parent
            Paper parent1 = select(pop);
            Paper parent2 = select(pop);
            while (parent2.getId() == parent1.getId()) {
                parent2 = select(pop);
            }
            // 交叉
            Paper child = crossover(parent1, parent2, examRule);
            child.setId(i);
            papers.add(i, child);
        }
        // 种群变异操作
        Paper tmpPaper;
        for (int i = elitismOffset; i < papers.size(); i++) {
            tmpPaper = papers.get(i);
            mutate(tmpPaper);
            // 计算知识点覆盖率与适应度
            tmpPaper.setKpCoverage(examRule);
            tmpPaper.setFitness(examRule, 0.3, 0.7);
        }
        return papers;
    }

    /**
     * TODO 交叉变异
     */
    public static Paper crossover(Paper parent1, Paper parent2, ExamRule examRule) {
        Paper child = new Paper(parent1.getQuestionCount());
        int s1 = (int) (Math.random() * parent1.getQuestionCount());
        int s2 = (int) (Math.random() * parent1.getQuestionCount());

        // parent1的startPos endPos之间的序列，会被遗传到下一代
        int startPos = s1 < s2 ? s1 : s2;
        int endPos = s1 > s2 ? s1 : s2;
        for (int i = startPos; i < endPos; i++) {
            child.setQuestion(i, parent1.getQuestion(i));
        }

        // 继承parent2中未被child继承的question
        // 防止出现重复的元素
        String idString = examRule.getKnowledgePoints().toString();
        for (int i = 0; i < startPos; i++) {
            if (!child.containsQuestion(parent2.getQuestion(i))) {
                child.setQuestion(i, parent2.getQuestion(i));
            } else {
                int type = getTypeByIndex(i, examRule);
                // TODO 从数据库中寻找符合条件的候选题目，通过type和标签
//                Question[] singleArray = QuestionService.getQuestionArray(type, idString.substring(1, idString.indexOf("]")));

                List<Question> singleArray = questionService.getQuestionByTag(type, examRule.getKnowledgePoints(), examRule.getLabel());
                child.setQuestion(i, singleArray.get((int) (Math.random() * singleArray.size() )));
            }
        }
        for (int i = endPos; i < parent2.getQuestionCount(); i++) {
            if (!child.containsQuestion(parent2.getQuestion(i))) {
                child.setQuestion(i, parent2.getQuestion(i));
            } else {
                int type = getTypeByIndex(i, examRule);
                // TODO 寻找符合条件的候选题目，通过type和标签
//                Question[] singleArray = QuestionService.getQuestionArray(type, idString.substring(1, idString.indexOf("]")));
                List<Question> singleArray = questionService.getQuestionByTag(type, examRule.getKnowledgePoints(), examRule.getLabel());
                child.setQuestion(i, singleArray.get((int) (Math.random() * singleArray.size() )));
            }
        }

        return child;
    }

    private static int getTypeByIndex(int index, ExamRule examRule) {
        int type;
        // 单选
        if (index < examRule.getSingleChoiceCount()) {
            type = 1;
        } else if (index < examRule.getSingleChoiceCount() + examRule.getGapFillingCount()) {
            // 填空
            type = 2;
        } else if (index < examRule.getSingleChoiceCount() + examRule.getGapFillingCount() + examRule.getCheckCount()) {
            // 判断
            type = 3;
        } else {
            // 编程
            type = 4;
        }
        return type;
    }

    /**
     * TODO 突变
     */
    public static void mutate(Paper paper) {
        Question tmpQuestion;
        List<Question> list;
        int index;
        for (int i = 0; i < paper.getQuestionCount(); i++) {
            if (Math.random() < mutationRate) {
                // 进行突变，第i道
                tmpQuestion = paper.getQuestion(i);
                // TODO 从题库中获取和变异的题目类型一样分数相同的题目（不包含变异题目）
                //list = QuestionService.getQuestionListWithOutSId(tmpQuestion);
                list = new ArrayList<>();
                if (list.size() > 0) {
                    // 随机获取一道
                    index = (int) (Math.random() * list.size());
                    // 设置分数
                    list.get(index).setScore(tmpQuestion.getScore());
                    paper.setQuestion(i, list.get(index));
                }
            }
        }
    }

    /**
     * TODO 选择
     */
    private static Paper select(Population population) {
        List<Paper> papers = new ArrayList<>();
        for (int i = 0; i < tournamentSize; i++) {
            papers.add(population.getPaper((int) (Math.random() * population.getLength())));
        }

        Paper paper = papers.get(0);
        for (int i = 1; i < papers.size(); i++) {
            if (paper.getFitness() < papers.get(i).getFitness()) {
                paper = papers.get(i);
            }
        }
        return paper;
    }
}
