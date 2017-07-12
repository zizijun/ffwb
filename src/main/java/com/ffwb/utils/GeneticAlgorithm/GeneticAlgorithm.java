package com.ffwb.utils.GeneticAlgorithm;

import com.ffwb.entity.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dearlhd on 2017/7/11.
 */
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

    /**
     * TODO 进化种群
     */
    public static Population evolvePopulation(Population pop, Rule rule) {
        Population newPopulation = new Population(pop.getLength());
        int elitismOffset;
        // 精英主义
        if (elitism) {
            elitismOffset = 1;
            // 保留上一代最优秀个体
            Paper fitness = pop.getFitness();
            fitness.setId(0);
            newPopulation.setPaper(0, fitness);
        }
        // 种群交叉操作，从当前的种群pop来创建下一代种群newPopulation
        for (int i = elitismOffset; i < newPopulation.getLength(); i++) {
            // 较优选择parent
            Paper parent1 = select(pop);
            Paper parent2 = select(pop);
            while (parent2.getId() == parent1.getId()) {
                parent2 = select(pop);
            }
            // 交叉
            Paper child = crossover(parent1, parent2, rule);
            child.setId(i);
            newPopulation.setPaper(i, child);
        }
        // 种群变异操作
        Paper tmpPaper;
        for (int i = elitismOffset; i < newPopulation.getLength(); i++) {
            tmpPaper = newPopulation.getPaper(i);
            mutate(tmpPaper);
            // 计算知识点覆盖率与适应度
            tmpPaper.setKpCoverage(rule);
            tmpPaper.setFitness(rule, 0.3, 0.7);
        }
        return newPopulation;
    }

    /**
     * TODO 交叉变异
     */
    public static Paper crossover(Paper parent1, Paper parent2, Rule rule) {
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
        String idString = rule.getKnowledgePoints().toString();
        for (int i = 0; i < startPos; i++) {
            if (!child.containsQuestion(parent2.getQuestion(i))) {
                child.setQuestion(i, parent2.getQuestion(i));
            } else {
                int type = getTypeByIndex(i, rule);
                // TODO 从数据库中寻找符合条件的候选题目，通过type和标签
//                Question[] singleArray = QuestionService.getQuestionArray(type, idString.substring(1, idString.indexOf("]")));
                Question[] singleArray = null;
                child.setQuestion(i, singleArray[(int) (Math.random() * singleArray.length)]);
            }
        }
        for (int i = endPos; i < parent2.getQuestionCount(); i++) {
            if (!child.containsQuestion(parent2.getQuestion(i))) {
                child.setQuestion(i, parent2.getQuestion(i));
            } else {
                int type = getTypeByIndex(i, rule);
                // TODO 寻找符合条件的候选题目，通过type和标签
//                Question[] singleArray = QuestionService.getQuestionArray(type, idString.substring(1, idString.indexOf("]")));
                Question[] singleArray = null;
                child.setQuestion(i, singleArray[(int) (Math.random() * singleArray.length)]);
            }
        }

        return child;
    }

    private static int getTypeByIndex(int index, Rule rule) {
        int type = 0;
        // 单选
        if (index < rule.getSingleChoiceCount()) {
            type = 1;
        } else if (index < rule.getSingleChoiceCount() + rule.getGapFillingCount()) {
            // 填空
            type = 2;
        } else if (index < rule.getSingleChoiceCount() + rule.getGapFillingCount() + rule.getCheckCount()) {
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
        Population pop = new Population(tournamentSize);
        for (int i = 0; i < tournamentSize; i++) {
            pop.setPaper(i, population.getPaper((int) (Math.random() * population.getLength())));
        }
        return pop.getFitness();
    }
}
