package com.ffwb.utils.GeneticAlgorithm;

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

        return null;
    }

    /**
     * TODO 交叉变异
     */
    public static Paper crossover(Paper parent1, Paper parent2, Rule rule) {

        return null;
    }

    /**
     * TODO 突变
     */
    public static void mutate(Paper paper) {

    }

    /**
     * TODO 选择
     */
    private static Paper select(Population population) {

        return null;
    }
}
