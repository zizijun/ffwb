package com.ffwb.controller;

import com.ffwb.DTO.QuestionDTO;
import com.ffwb.entity.Exam;
import com.ffwb.model.ServiceResult;
import com.ffwb.service.ExamService;
import com.ffwb.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by dearlhd on 2017/7/3.
 */
@Controller
public class ExamController extends ApiController{
    @Autowired
    private ExamService examService;

    @Autowired
    private QuestionService questionService;

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
    public ServiceResult generateExam (@RequestBody Object obj) throws Exception {
        List<QuestionDTO> qList = questionService.getAllQuestions(1, 10, "id", "DESC").getList();

        return ServiceResult.success(null);
    }
}
