package com.ffwb.controller;

import com.ffwb.DTO.ExamDTO;
import com.ffwb.DTO.PaperDTO;
import com.ffwb.DTO.QuestionDTO;
import com.ffwb.dao.ExamDao;
import com.ffwb.entity.Answer;
import com.ffwb.entity.Exam;
import com.ffwb.entity.Question;
import com.ffwb.entity.User;
import com.ffwb.model.ServiceResult;
import com.ffwb.service.AnswerService;
import com.ffwb.service.ExamService;
import com.ffwb.service.QuestionService;
import com.ffwb.service.UserService;
import com.ffwb.utils.JsonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dearlhd on 2017/7/3.
 */
@Controller
public class ExamController extends ApiController{
    @Autowired
    private ExamService examService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private ExamDao examDao;

    /**
     * 创建新试卷
     */
    @RequestMapping(value = "/exam/create", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult createExam (@RequestBody Exam exam) throws Exception {
        checkParameter(exam !=null, "Exam can't be empty!");
        Exam examRet = examService.createExam(exam);
        if (examRet.getId() == -1) {
            return ServiceResult.fail( 403, exam);   // 已创建
        }
        return ServiceResult.success(examRet);
    }

    /**
     * 生成试题
     */
    @RequestMapping(value = "/exam/generate", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult generateExam (@RequestBody Exam exam) throws Exception {
        exam = examService.findExamById(exam.getId());
        User user = exam.getUser();

        PaperDTO paperDTO = new PaperDTO();
        paperDTO.setExam(exam);

        List<Answer> answers = answerService.getAnswersByExam(exam);
        if (answers != null && answers.size() != 0) {
            List<Question> questions = new ArrayList<>();
            for (Answer answer : answers) {
                questions.add(answer.getQuestion());
            }
            List<QuestionDTO> questionDTOs = question2Dto(questions);
            for (int i = 0; i < questionDTOs.size(); i++) {
                questionDTOs.get(i).setAnswerId(answers.get(i).getId());
            }
            paperDTO.setQuestions(questionDTOs);
            return ServiceResult.success(paperDTO);
        }

        // 组卷，获取题目
        List<Question> questions = examService.formPaper(exam);
        List<QuestionDTO> questionDTOs = question2Dto(questions);

        // 生成对应的空的解答
        for (int i = 0; i < questions.size(); i++) {
            Answer answer = new Answer();
            answer.setExam(exam);
            answer.setUser(user);
            answer.setQuestion(questions.get(i));
            answer.setAlive(1);
            answer = answerService.addAnswer(answer);
            questionDTOs.get(i).setAnswerId(answer.getId());
        }

        paperDTO.setQuestions(questionDTOs);

        int totalPoint = 0;
        for (Question question : questions){
            totalPoint += question.getScore();
        }
        exam.setTotalScore(totalPoint);
        exam = examDao.save(exam);
        paperDTO.setExam(exam);

        return ServiceResult.success(paperDTO);
    }

    /**
     * 获取某一个用户的所有试卷
     */
    @RequestMapping(value = "/exam/all", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getAllExam (@RequestParam(value="userId", defaultValue="0") int userId) throws Exception {
        User user = userService.findUserById(userId);
        List<Exam> exams = examService.getExamsByUser(user);
        return ServiceResult.success(exams);
    }

    /**
     * 提交答案
     */
    @RequestMapping(value = "/answer/add", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult resolve (@RequestBody List<Answer> answers) throws Exception {
        int count = answerService.updateAnswers(answers); // ret 为空是表示不存在
        String str = "成功提交了" + count + "条回答,失败" + (answers.size() - count) + "条。" ;
        return ServiceResult.success(str);
    }

    /**
     * 结束考试
     */
    @RequestMapping(value = "/exam/finish", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult finishExam (@RequestBody Exam exam) throws Exception {
        checkParameter(exam!=null, "exam is null");
        exam = examService.finishExam(exam);
        // TODO 批改
        exam = examService.checkAnswers(exam);
        return ServiceResult.success(exam);
    }

    /**
     * question2dto
     * @param questions
     * @return
     */
    private List<QuestionDTO> question2Dto(List<Question> questions) {
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question question :questions){
            QuestionDTO dto = new QuestionDTO();
            dto.setDescription(question.getDescription());
            dto.setId(question.getId());
            dto.setSolution(question.getSolution());
            dto.setType(question.getType());
            dto.setLabel(question.getLabel());
            if(question.getOptionJson() != null){
                Map map = JsonType.getData(question.getOptionJson());
                dto.setOptionJson(map);
            }

            dto.setTags(question.getTags());
            questionDTOList.add(dto);
        }
        return questionDTOList;
    }
}
