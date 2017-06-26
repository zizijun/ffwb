package com.ffwb.controller;

import com.ffwb.DTO.QuestionDTO;
import com.ffwb.entity.Question;
import com.ffwb.model.PageListModel;
import com.ffwb.model.ServiceResult;
import com.ffwb.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

/**
 * Created by jinchuyang on 2017/6/22.
 */
@Controller
public class QuestionController extends ApiController{

    @Autowired
    private QuestionService questionService;

    /**
     * 上传试题 试题存在excel之中
     * @param file
     * @param managerId
     * @return
     * @throws IOException
     */
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

    /**
     * 获取所有的试题(分页操作)
     * @return
     */
    @RequestMapping(value = "/questions/getAll", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getAllQuestions(@RequestParam(value="pageIndex", defaultValue="0") int pageIndex,
                                         @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                                         @RequestParam(value="sortField", defaultValue="id") String sortField,
                                         @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){
        //TODO
        PageListModel questions = questionService.getAllQuestions(pageIndex, pageSize, sortField, sortOrder);
        return ServiceResult.success(questions);
    }

    public ServiceResult getQuestionsByConditions(@RequestParam(value = "label", defaultValue = "") String label,
                                                  @RequestParam(value = "type", defaultValue = "")String type,
                                                  @RequestParam(value="pageIndex", defaultValue="0") int pageIndex,
                                                  @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                                                  @RequestParam(value="sortField", defaultValue="id") String sortField,
                                                  @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){
        //TODO
        return ServiceResult.success(null);
    }

}
