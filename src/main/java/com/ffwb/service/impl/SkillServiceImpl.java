package com.ffwb.service.impl;

import com.ffwb.DTO.SkillModel;
import com.ffwb.dao.AnswerDao;
import com.ffwb.dao.SkillDao;
import com.ffwb.dao.TagDao;
import com.ffwb.dao.UserDao;
import com.ffwb.entity.*;
import com.ffwb.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    @Autowired
    private TagDao tagDao;



    long[] j2eeIds = new long[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 18, 19, 20, 22};
    long[] j2seIds = new long[]{13,14,1617,21,36};
    long[] htmlIds = new long[]{23,34};
    long[] cssIds = new long[]{24,27,28};
    long[] jsIds = new long[]{25,26,29,30,31,32,33,35};

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

            //针对每一个tag做分析
            for (Map.Entry<Tag, List<Answer>> entry : maps.entrySet()){
                double totalPoints = 0, getpoint = 0;
                double totaoDifficult = 0;
                int correctNum = 0;
                for (Answer answer : entry.getValue()){
                    //TODO 算法可改进
                    Question q = answer.getQuestion();
                    totaoDifficult += q.getDifficulty();
                    if (answer.isRight()){
                        getpoint += q.getDifficulty() * q.getScore();
                        correctNum++;
                    }
                    totalPoints += q.getDifficulty() * q.getScore();
                }
                Skill skill = skillDao.findByTagAndUserAndAlive(entry.getKey(), user, 1);
                if (skill==null){
                    skill = new Skill();
                    skill.setAlive(1);
                    skill.setTag(entry.getKey());
                    skill.setNumber(answers.size());
                    if (answers.size() ==0){
                        skill.setDifficult(0);
                        skill.setCorrect(0);
                    }else {
                        skill.setDifficult(totaoDifficult/answers.size());
                        skill.setDifficult(correctNum/answers.size());
                    }
                    skill.setUser(user);
                }
                skill.setPoint(getpoint/(totalPoints*1.0)*100);
                skillDao.save(skill);
            }
        }
    }

    @Override
    public List<SkillModel> getSkillModel(Long userId) {
        List<SkillModel> skillModels = new ArrayList<>();
        User user = userDao.findByIdAndAlive(userId, 1);
        if (user == null){
            return null;
        }
        Set<Tag> j2eeTagSet = getTagsByIds(j2eeIds);
        Set<Tag> j2seTagSet = getTagsByIds(j2seIds);
        Set<Tag> htmlTagSet = getTagsByIds(htmlIds);
        Set<Tag> cssTagSet = getTagsByIds(cssIds);
        Set<Tag> jsTagSet = getTagsByIds(jsIds);
        List<Skill> j2eeSkills = getSkillsByCondition(user, j2eeTagSet);
        List<Skill> j2eeSkills1 = getSkillsByCondition(null, j2eeTagSet);
        SkillModel j2eeModel = skillList2Model(j2eeSkills, "j2ee", j2eeSkills1);

        List<Skill> j2seSkills = getSkillsByCondition(user, j2seTagSet);
        List<Skill> j2seSkills1 = getSkillsByCondition(null, j2seTagSet);
        SkillModel j2seModel = skillList2Model(j2seSkills, "j2se", j2seSkills1);

        List<Skill> htmlSkills = getSkillsByCondition(user, htmlTagSet);
        List<Skill> htmlSkills1 = getSkillsByCondition(null, htmlTagSet);
        SkillModel htmlModel = skillList2Model(htmlSkills, "html", htmlSkills1);

        List<Skill> cssSkills = getSkillsByCondition(user, cssTagSet);
        List<Skill> cssSkills1 = getSkillsByCondition(null, cssTagSet);
        SkillModel cssModel = skillList2Model(cssSkills, "css", cssSkills1);

        List<Skill> jsSkills = getSkillsByCondition(user, jsTagSet);
        List<Skill> jsSkills1 = getSkillsByCondition(null, jsTagSet);
        SkillModel jsModel = skillList2Model(jsSkills, "js", jsSkills1);

        skillModels.add(j2eeModel);
        skillModels.add(j2seModel);
        skillModels.add(htmlModel);
        skillModels.add(cssModel);
        skillModels.add(jsModel);

        return skillModels;
    }

    @Override
    public List<SkillModel> getTotalSkillModel(long id) {
        List<SkillModel> skillModels = new ArrayList<>();
        User user = userDao.findByIdAndAlive(id, 1);
        if (user == null){
            return null;
        }
        List<Tag> tags = tagDao.findByAlive(1);
        for (Tag tag : tags){
            SkillModel skillModel = new SkillModel();
            Set<Tag> tagSet = new HashSet<>();
            tagSet.add(tag);
            List<Skill> skills = skillDao.findByTagAndAlive(tag, 1);
            boolean isMatched = false;
            for (Skill skill : skills){
                if (user.equals(skill.getUser())){
                    skillModel.setValue(skill.getPoint());
                    skillModel.setCorrect(skill.getCorrect());
                    skillModel.setNumber(skill.getNumber());
                    skillModel.setName(skill.getTag().getContent());
                    skillModel.setDifficult(skill.getDifficult());
                    isMatched = true;
                    break;
                }
            }
            if (!isMatched){
                continue;
            }
            double averagePoint = getAveragePoint(skills);
            skillModel.setAverage(averagePoint);
            skillModels.add(skillModel);
        }
        return skillModels;
    }

    @Override
    public void createRandomData() {
        List<User> users = userDao.findByAlive(1);
        List<Tag> tags = tagDao.findByAlive(1);
        List<Skill> skills = new ArrayList<>();
        int a = 1, b = 1;
        for (User user : users){
            for (Tag tag: tags) {
                final long l = System.currentTimeMillis();
                Random random = new Random(l+a*b);//指定种子数字
                int num = random.nextInt(20);
                Skill skill = new Skill();
                skill.setUser(user);
                skill.setTag(tag);
                skill.setCorrect(random.nextDouble());
                skill.setPoint(random.nextDouble()*3);
                skill.setNumber(random.nextInt(100));
                skill.setDifficult(random.nextDouble()*3);
                skill.setAlive(1);
                skills.add(skill);
                a++;
            }
            b++;
        }
        skillDao.save(skills);
    }

    private double getAveragePoint(List<Skill> j2eeSkills1) {
        int number = 0;
        double pointNum = 0;
        for (Skill skill : j2eeSkills1){
            number += skill.getNumber();
            pointNum += skill.getNumber() * skill.getPoint();
        }
        if (number !=0){
            return pointNum /number;
        }else {
            return 0;
        }
    }

    private SkillModel skillList2Model(List<Skill> skills, String name, List<Skill> skills1) {
        SkillModel model = new SkillModel();
        model.setName(name);
        int number = 0;
        double correctNum =0;
        double difficultNum = 0;
        double pointNum = 0;
        for (Skill skill : skills){
            number += skill.getNumber();
            correctNum += skill.getNumber() * skill.getCorrect();
            difficultNum += skill.getNumber() * skill.getDifficult();
            pointNum += skill.getNumber() * skill.getPoint();
        }
        model.setNumber(number);
        if (number ==0){
            model.setCorrect(0);
            model.setDifficult(0);
            model.setValue(0);
        }else {
            model.setCorrect(correctNum / number);
            model.setDifficult(difficultNum / number);
            model.setValue(pointNum / number);
        }
        double averagePoint = getAveragePoint(skills1);
        model.setAverage(averagePoint);
        return model;
    }

    public List<Skill> getSkillsByCondition(User user, Set<Tag> tags){
        Specification<Skill> specification = this.buildSpecifications(user,tags,1);
        List<Skill> skills = skillDao.findAll(Specifications.where(specification));
        return skills;
    }

    private Specification<Skill> buildSpecifications(User user, Set<Tag> tags, int alive){
        final User fUser = user;
        final Set<Tag> fTags= tags;
        final int fAlive = alive;

        Specification<Skill> specification = new Specification<Skill>(){
            @Override
            public Predicate toPredicate(Root<Skill> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();

                predicate.getExpressions().add(criteriaBuilder.equal(root.get("alive"),fAlive));
                if (fUser != null){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<User>get("user"),fUser));
                }
                if (fTags != null && fTags.size() != 0){
                    predicate.getExpressions().add(criteriaBuilder.isTrue(root.<Tag>get("tag").in(fTags)));
                }

                return criteriaBuilder.and(predicate);
            }

        };
        return specification;
    }

    private Set<Tag> getTagsByIds(long[] ids){
        Set<Tag> tagSet = new HashSet<>();
        for(int i = 0; i < ids.length; i++){
            Tag tag = tagDao.findOne(ids[i]);
            tagSet.add(tag);
        }
        return tagSet;
    }
}
