package com.ffwb.utils.GeneticAlgorithm;

import com.ffwb.entity.Question;
import com.ffwb.entity.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dearlhd on 2017/7/11.
 * 遗传算法中的染色体
 * 用以表示一份试卷
 */
public class Paper {

    /**
     * 试卷id
     */
    private long id;

    /**
     * 试题内容
     */
    private List<Question> questions;

    /**
     * 试卷总分
     */
    private int totalScore = 0;

    /**
     * 适应度
     */
    private double fitness = 0.0;

    /**
     * 试卷知识点覆盖率
     */
    private double kPCoverage = 0.0;

    /**
     * 试卷难度
     */
    private double difficulty = 0.0;

    public Paper() {
        questions = new ArrayList<>();
    }

    public Paper (int size) {
        questions = new ArrayList<>(size);
    }

    public int getTotalScore () {
        if (totalScore != 0) {
            return totalScore;
        }
        for (Question question : questions) {
            totalScore += question.getScore();
        }

        return totalScore;
    }

    /**
     * 计算试卷个体难度系数
     * 计算公式： 每题难度*分数求和除总分
     */
    public double getDifficulty() {
        if (difficulty == 0) {
            double _difficulty = 0;
            for (Question question : questions) {
                _difficulty += question.getScore() * question.getDifficulty();
            }
            difficulty = _difficulty / getTotalScore();
        }
        return difficulty;
    }

    /**
     * 获取试题数量
     */
    public int getQuestionCount () {
        return questions.size();
    }

    /**
     * TODO 计算知识点覆盖率
     * 计算公式：个体包含的知识点 / 期望包含的知识点
     */
    public void setKpCoverage (ExamRule rule) {
        if (kPCoverage == 0.0) {
            Set<String> result = new HashSet<>();
            List<String> points = new ArrayList<>();
            List<Tag> tags = rule.getKnowledgePoints();
            for (Tag tag : tags) {
                String point = tag.getContent();
                points.add(point);
            }
            result.addAll(points);
            Set<String> another = questions.stream().map(question -> String.valueOf(question.getTags())).collect(Collectors.toSet());
            // 交集操作
            result.retainAll(another);
            kPCoverage = result.size() / rule.getKnowledgePoints().size();
        }
    }

    public double getkPCoverage () {
        return kPCoverage;
    }
    /**
     * TODO 计算个体适应度
     * 计算公式：f=1-(1-M/N)*f1-|EP-P|*f2
     * 其中M/N为知识点覆盖率，EP为期望难度系数，P为种群个体难度系数，f1为知识点分布的权重
     * ，f2为难度系数所占权重。当f1=0时退化为只限制试题难度系数，当f2=0时退化为只限制知识点分布
     *
     * @param examRule 组卷规则
     * @param f1   知识点分布的权重
     * @param f2   难度系数的权重
     */
    public void setFitness(ExamRule examRule, double f1, double f2) {
        if (fitness == 0) {
            fitness = 1 - (1 - getkPCoverage()) * f1 - Math.abs(examRule.getDifficulty() - getDifficulty()) * f2;
        }
    }

    public double getFitness () {
        return fitness;
    }

    /**
     * 判断试卷中是否包含某个题目
     */
    public boolean containsQuestion (Question question) {
        if (question == null) {
            for (Question q : questions) {
                if (q == null) {
                    return  true;
                }
            }
        }
        else {
            for (Question q : questions) {
                if (q != null) {
                    if (q.equals(question)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 设置问题
     */
    public void setQuestion (int index, Question question) {
        questions.set(index, question);

        totalScore = 0;
        fitness = 0.0;
        difficulty = 0.0;
        kPCoverage = 0.0;
    }

    /**
     * 增加问题
     */
    public void addQuestion (Question question) {
        questions.add(question);

        totalScore = 0;
        fitness = 0.0;
        difficulty = 0.0;
        kPCoverage = 0.0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Question getQuestion(int index) {
        if (questions != null && questions.size() > index) {
            return questions.get(index);
        }
        return null;
    }
}
