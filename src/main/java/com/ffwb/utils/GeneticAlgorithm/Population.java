package com.ffwb.utils.GeneticAlgorithm;

import com.ffwb.entity.Question;
import com.ffwb.service.QuestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dearlhd on 2017/7/11.
 * 遗传算法中的种群
 * 表示多份试卷的合集
 */
public class Population {
    private List<Paper> papers;

    public Population (int size, Rule rule) {
        papers = new ArrayList<>(size);
        Paper paper;
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            paper = new Paper();
            paper.setId(i + 1);
            while (paper.getTotalScore() != rule.getTotalScore()) {
                paper.getQuestions().clear();
                String idString = rule.getKnowledgePoints().toString();
                // 单选题
                if (rule.getSingleChoiceCount() > 0) {
                    generateQuestion(1, random, rule.getSingleChoiceCount(), 2, idString,
                            "单选题数量不够，组卷失败", paper);
                }
                // 填空题
                if (rule.getGapFillingCount() > 0) {
                    generateQuestion(2, random, rule.getGapFillingCount(), 2, idString,
                            "填空题数量不够，组卷失败", paper);
                }

                // 判断题
                if (rule.getCheckCount() > 0) {
                    generateQuestion(3, random, rule.getCheckCount(), 2, idString,
                            "填空题数量不够，组卷失败", paper);
                }
                // 编程题
                if (rule.getProgrammingCount() > 0) {
                    generateQuestion(4, random, rule.getProgrammingCount(), 15, idString,
                            "编程题数量不够，组卷失败", paper);
                }
            }
            // 计算试卷知识点覆盖率
            paper.setKpCoverage(rule);
            // TODO 计算试卷适应度
            paper.setFitness(rule, 0.3, 0.7);
            papers.set(i, paper);
        }
    }

    /**
     * 初始化试题
     * TODO 如何初始化
     */
    private void generateQuestion(int type, Random random, int questionCount, int score, String labels,
                                  String errorMsg, Paper paper) {
        // TODO 从数据库获取试题库
        Question[] questions = null;
        if (questions.length < questionCount) {
            return;
        }
        Question tmpQuestion;
        for (int j = 0; j < questionCount; j++) {
            int index = random.nextInt(questions.length - j);
            // 初始化分数
            questions[index].setScore(score);
            paper.addQuestion(questions[index]);
            // 保证不会重复添加试题
            tmpQuestion = questions[questions.length - j - 1];
            questions[questions.length - j - 1] = questions[index];
            questions[index] = tmpQuestion;
        }
    }

    /**
     * 获取种群中最优秀个体
     *
     * @return
     */
    public Paper getFitness() {
        Paper paper = papers.get(0);
        for (int i = 1; i < papers.size(); i++) {
            if (paper.getFitness() < papers.get(i).getFitness()) {
                paper = papers.get(i);
            }
        }
        return paper;
    }

    /**
     * 获取种群中某个个体
     *
     * @param index
     * @return
     */
    public Paper getPaper(int index) {
        return papers.get(index);
    }

    /**
     * 设置种群中某个个体
     *
     * @param index
     * @param paper
     */
    public void setPaper(int index, Paper paper) {
        papers.set(index, paper);
    }

    /**
     * 返回种群规模
     *
     * @return
     */
    public int getLength() {
        return papers.size();
    }
}
