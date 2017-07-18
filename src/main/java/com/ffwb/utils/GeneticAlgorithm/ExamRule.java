package com.ffwb.utils.GeneticAlgorithm;

import com.ffwb.entity.Tag;

import java.util.List;

/**
 * Created by dearlhd on 2017/7/11.
 * 组卷规则
 */
public class ExamRule {

    /**
     * 试卷总分
     */
    private int totalScore;

    /**
     * 试卷期望难度系数
     */
    private double difficulty;

    /**
     * 单选题数量
     */
    private int singleChoiceCount;

    /**
     * 填空题数量
     */
    private int gapFillingCount;

    /**
     * 判断题数量
     */
    private int checkCount;

    /**
     * 编程题数量
     */
    private int programmingCount;

    /**
     * 试卷包含的知识点
     */
    private List<Tag> knowledgePoints;

    public ExamRule() {

    }

    public ExamRule (int totalScore, double difficulty, int singleChoiceCount, int gapFillingCount, int checkCount, int programmingCount, List<Tag> knowledgePoints) {
        this.totalScore = totalScore;
        this.difficulty = difficulty;
        this.singleChoiceCount = singleChoiceCount;
        this.gapFillingCount = gapFillingCount;
        this.checkCount = checkCount;
        this.programmingCount = programmingCount;
        this.knowledgePoints = knowledgePoints;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public int getSingleChoiceCount() {
        return singleChoiceCount;
    }

    public void setSingleChoiceCount(int singleChoiceCount) {
        this.singleChoiceCount = singleChoiceCount;
    }

    public int getGapFillingCount() {
        return gapFillingCount;
    }

    public void setGapFillingCount(int gapFillingCount) {
        this.gapFillingCount = gapFillingCount;
    }

    public int getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(int checkCount) {
        this.checkCount = checkCount;
    }

    public int getProgrammingCount() {
        return programmingCount;
    }

    public void setProgrammingCount(int programmingCount) {
        this.programmingCount = programmingCount;
    }

    public List<Tag> getKnowledgePoints() {
        return knowledgePoints;
    }

    public void setKnowledgePoints(List<Tag> knowledgePoints) {
        this.knowledgePoints = knowledgePoints;
    }
}
