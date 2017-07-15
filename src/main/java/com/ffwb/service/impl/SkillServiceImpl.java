package com.ffwb.service.impl;

import com.ffwb.dao.AnswerDao;
import com.ffwb.dao.SkillDao;
import com.ffwb.dao.UserDao;
import com.ffwb.entity.*;
import com.ffwb.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by jinchuyang on 2017/7/14.
 */
@Service("SkillService")
public class SkillServiceImpl implements SkillService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private AnswerDao answerDao;
    @Autowired
    private SkillDao skillDao;

    /**
     * 能力模型构建
     */
    @Override
    public void analyzeSkill() {
        List<User> users = userDao.findByAlive(1);
        for (User user : users){
            List<Answer> answers = answerDao.findByUserAndAlive(user, 1);
            Map<Tag, List<Answer>> maps = new HashMap<>();
            //将每一个人的answer根据tag分类
            for (Answer answer : answers){
                Question question = answer.getQuestion();
                Set<Tag> tags = question.getTags();
                for (Tag tag : tags){
                    if (!maps.containsKey(tag)){
                        maps.put(tag, new ArrayList<>());
                    }
                    List<Answer> answerList = maps.get(tag);
                    answerList.add(answer);
                    maps.put(tag, answerList);
                }
            }
            double totalPoints = 0, getpoint = 0;
            //针对每一个tag做分析
            for (Map.Entry<Tag, List<Answer>> entry : maps.entrySet()){
                for (Answer answer : entry.getValue()){
                    //TODO 算法可改进
                    Question q = answer.getQuestion();
                    if (answer.isRight()){
                        getpoint += q.getDifficulty() * q.getScore();
                    }
                    totalPoints += q.getDifficulty() * q.getScore();
                }
                Skill skill = skillDao.findByTagAndUserAndAlive(entry.getKey(), user, 1);
                if (skill==null){
                    skill = new Skill();
                    skill.setAlive(1);
                    skill.setTag(entry.getKey());
                    skill.setUser(user);
                }
                skill.setPoint(getpoint/(totalPoints*1.0));
                skillDao.save(skill);
            }

        }
    }
}
