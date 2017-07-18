package com.ffwb.utils.GeneticAlgorithm;

import com.ffwb.entity.Question;
import com.ffwb.entity.Tag;
import com.ffwb.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dearlhd on 2017/7/11.
 * 遗传算法中的种群
 * 表示多份试卷的合集
 */
@Component
public class Population {
    @Autowired
    private QuestionService questionService;

    private List<Paper> papers;

    public void init (int size) {
        papers = new ArrayList<>(size);
    }

    public void init (int size, ExamRule rule) {
        papers = new ArrayList<>();
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
                    generateQuestion(1, random, rule.getSingleChoiceCount(), 2, rule.getKnowledgePoints(),
                            "单选题数量不够，组卷失败", paper);
                }
                // 填空题
                if (rule.getGapFillingCount() > 0) {
                    generateQuestion(2, random, rule.getGapFillingCount(), 2, rule.getKnowledgePoints(),
                            "填空题数量不够，组卷失败", paper);
                }

                // 判断题
                if (rule.getCheckCount() > 0) {
                    generateQuestion(3, random, rule.getCheckCount(), 2, rule.getKnowledgePoints(),
                            "判断题数量不够，组卷失败", paper);
                }
                // 编程题
                if (rule.getProgrammingCount() > 0) {
                    generateQuestion(4, random, rule.getProgrammingCount(), 15, rule.getKnowledgePoints(),
                            "编程题数量不够，组卷失败", paper);
                }
            }
            // 计算试卷知识点覆盖率
            paper.setKpCoverage(rule);
            // TODO 计算试卷适应度
            paper.setFitness(rule, 0.3, 0.7);
            papers.add(paper);
        }
    }

    /**
     * 初始化试题
     * TODO 如何初始化
     */
    private void generateQuestion(int type, Random random, int questionCount, int score, List<Tag> tags,
                                  String errorMsg, Paper paper) {
        List<Question> questions = questionService.getQuestionByTag(type, tags);
        if (questions == null || questions.size() < questionCount) {
            return;
        }
        Question tmpQuestion;
        for (int j = 0; j < questionCount; j++) {
            int index = random.nextInt(questions.size() - j);
            // 初始化分数
            questions.get(index).setScore(score);
            paper.addQuestion(questions.get(index));
            // 保证不会重复添加试题
            tmpQuestion = questions.get(questions.size() - j - 1);
            questions.set(questions.size() - j - 1, questions.get(index));
            questions.set(index, tmpQuestion);
        }
    }

    /**
     * 获取种群中最优秀个体
     *
     * @return
     */
    public Paper getFittest() {
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

    public void setPapers (List<Paper> papers) {
        this.papers = papers;
    }

    public List<Paper> getPapers () {
        return papers;
    }
}
