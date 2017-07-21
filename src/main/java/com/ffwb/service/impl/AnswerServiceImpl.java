package com.ffwb.service.impl;

import com.ffwb.dao.AnswerDao;
import com.ffwb.dao.ExamDao;
import com.ffwb.entity.Answer;
import com.ffwb.entity.Exam;
import com.ffwb.entity.Question;
import com.ffwb.hanlp.HanLP;
import com.ffwb.hanlp.dictionary.CustomDictionary;
import com.ffwb.hanlp.seg.common.Term;
import com.ffwb.service.AnswerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;


/**
 * Created by dearlhd on 2017/7/3.
 */
@Service("AnswerSerivce")
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    AnswerDao answerDao;

    protected final Log logger = LogFactory.getLog(getClass());
    private  String keywordDictionaryPath = "data/dictionary/keyword/keyword.txt";
    @Override
    public Answer addAnswer (Answer answer) {
        Answer temp = answerDao.findByExamAndQuestionAndAlive(answer.getExam(), answer.getQuestion(), answer.getAlive());
        if (temp != null) {
            return null;
        }
        return answerDao.save(answer);
    }

    @Override
    public Answer updateAnswer (Answer answer) {
        Answer temp = answerDao.findByExamAndQuestionAndAlive(answer.getExam(), answer.getQuestion(), answer.getAlive());
        if (temp == null) {
            return null;
        }
        temp.setAnswer(answer.getAnswer());
        return answerDao.save(temp);
    }

    @Override
    public int updateAnswers (List<Answer> answers) {
        int successCount = 0;
        for (Answer answer : answers) {
            Answer temp = answerDao.findByIdAndAlive(answer.getId(), 1);
            if (temp == null) {
                continue;
            }
            temp.setAnswer(answer.getAnswer());
            answerDao.save(temp);
            successCount++;
        }

        return successCount;
    }

    @Override
    public boolean deleteAnswer(Answer answer) {
        answer.setAlive(0);
        if (updateAnswer(answer) == null) {
            return false;
        }
        return true;
    }

    @Override
    public List<Answer> getAnswersByExam (Exam exam) {
        List<Answer> answers = answerDao.findByExamAndAlive(exam, 1);
        return answers;
    }

    @Override
    public int judgeAnswers(Answer answer) throws IOException {
        Question question = answer.getQuestion();
        int score =0;
        boolean right = false;
        if(question.getType() ==1){ //选择题
            String[] solutions = question.getSolution().split(" ");
            String[] answers = answer.getAnswer().split(" ");
            if (solutions.length == answers.length){
                boolean isRight = true;
                for (String str : solutions){
                    if(!Arrays.asList(answers).contains(str)){
                        isRight = false;
                    }
                }
                if (isRight){
                    score = question.getScore();
                    right = true;
                }
            }

        }
        if (question.getType() == 3){//判断题
            if(question.getSolution().equals(answer.getAnswer())){
                score = question.getScore();
                right = true;
            }
        }
        if (question.getType() == 5){//简答题
            List<String> keywords = getKeywords();
            for (String keyword: keywords) {
                CustomDictionary.add(keyword);
            }
            String solution = question.getSolution();
            String answerContent = answer.getAnswer();

            CustomDictionary.add("封装");
            CustomDictionary.add("继承");
            CustomDictionary.add("多态");

            //分词
            List<Term> solutionTerms = HanLP.segment(solution);
            List<Term> answerTerms = HanLP.segment(answerContent);
            Set<String> solutionKeywords = new HashSet<>();
            Set<String> answerKeywords = new HashSet<>();

            for (Term term: solutionTerms){//获取答案中关键词个数
                if (keywords.contains(term.word)){
                    solutionKeywords.add(term.word);
                }
            }
            for (Term term: answerTerms){//获取解答中关键词个数
                if (keywords.contains(term.word)){
                    answerKeywords.add(term.word);
                }
            }
            score = (int)Math.round(question.getScore() * (answerKeywords.size())/(solutionKeywords.size() *1.0));
            if (score > 0)
                right = true;


        }
        answer.setScore(score);
        answer.setRight(right);
        answerDao.save(answer);
        return score;
        //return 0;

    }

    private List<String> getKeywords() throws IOException {
        List<String> keywords = new ArrayList<>();

        File input = new File(keywordDictionaryPath);
        InputStream is = new FileInputStream(input);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        //StringBuffer sb = new StringBuffer();
        String temp;
        while((temp = br.readLine()) != null) {
            keywords.add(temp);
        }
        return keywords;
    }
}
