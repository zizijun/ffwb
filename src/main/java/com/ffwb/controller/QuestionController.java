package com.ffwb.controller;

import com.ffwb.model.ServiceResult;
import com.ffwb.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by jinchuyang on 2017/6/22.
 */
@Controller
public class QuestionController extends ApiController{

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/question/upload", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult uploadQuestion(@RequestParam(value = "file")MultipartFile file,
                                        @RequestParam(value = "managerId", defaultValue = "-1")Long managerId) throws IOException {
        checkParameter(file!=null ,"file is empty");
        checkParameter(managerId != null,"manager is empty");
        int uploadNum = questionService.upload(file, managerId);
        if (uploadNum > 0){
            return ServiceResult.success("成功上传"+uploadNum+"题目");

        }else {
            return ServiceResult.fail(500,"上传失败");
        }
    }
}
