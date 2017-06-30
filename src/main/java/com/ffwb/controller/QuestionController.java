package com.ffwb.controller;

import com.ffwb.DTO.QuestionDTO;
import com.ffwb.entity.Manager;
import com.ffwb.entity.Question;
import com.ffwb.model.PageListModel;
import com.ffwb.model.ServiceResult;
import com.ffwb.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    @RequestMapping(value = "/questions/all", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getAllQuestions(@RequestParam(value="pageIndex", defaultValue="0") int pageIndex,
                                         @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                                         @RequestParam(value="sortField", defaultValue="id") String sortField,
                                         @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){
        //TODO
        PageListModel questions = questionService.getAllQuestions(pageIndex, pageSize, sortField, sortOrder);
        return ServiceResult.success(questions);
    }

    /**
     * 根据条件搜索问题
     * @param label
     * @param type
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    @RequestMapping(value = "/questions/search", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getQuestionsByConditions(@RequestParam(value = "label", defaultValue = "") String label,
                                                  @RequestParam(value = "type", defaultValue = "")String type,
                                                  @RequestParam(value="pageIndex", defaultValue="0") int pageIndex,
                                                  @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                                                  @RequestParam(value="sortField", defaultValue="id") String sortField,
                                                  @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){
        //TODO
        System.out.println("label: "+label +"   "+"type:"+type);
        PageListModel questions = questionService.getQuestionsByConditions(label, type, pageIndex, pageSize, sortField, sortOrder);
        return ServiceResult.success(questions);
    }

    /*@RequestMapping(value="/questions/label",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult labelQuestion(@RequestParam(value="label",defaultValue = "")String label,
                                       @RequestParam(value="id",defaultValue = "-1")Long id){
        System.out.println("label:"+label);
        boolean res=questionService.labelQuestions(label,id);
        return ServiceResult.success("加入标签"+res);
    }*/

    @RequestMapping(value="/questions/label",method=RequestMethod.PUT)
    @ResponseBody
    public ServiceResult labelQuestion(@RequestBody List<QuestionDTO> dtolist)throws Exception{
        checkParameter(dtolist!=null,"question list cannot be null");
        boolean flag=questionService.labelQuestions(dtolist);
        return ServiceResult.success("修改标签结果"+flag);
    }

    @RequestMapping(value="/questions/update",method=RequestMethod.PUT)
    @ResponseBody
    public ServiceResult updateQuestion(@RequestBody List<QuestionDTO> dtolist)throws Exception{
        checkParameter(dtolist!=null,"question to be updated is null");
        boolean flag=questionService.updateQuestions(dtolist);
        return ServiceResult.success("更新问题结果"+flag);
    }

    @RequestMapping(value="/questions/add",method=RequestMethod.POST)
    @ResponseBody
    public ServiceResult addQuestion(@RequestBody QuestionDTO dto, @RequestParam(value="managerId",defaultValue = "-1")Long managerId)throws Exception{
        checkParameter(dto!=null,"question to be added is null");
        boolean flag=questionService.addQuestions(dto,managerId);
        return ServiceResult.success("添加问题结果"+flag);
    }

    @RequestMapping(value="/questions/delete",method=RequestMethod.PUT)
    @ResponseBody
    public ServiceResult deleteQuestion(@RequestBody List<QuestionDTO> dtolist)throws Exception{
        checkParameter(dtolist!=null,"question to be deleted is null");
        boolean flag=questionService.deleteQuestions(dtolist);
        return ServiceResult.success("删除问题结果"+flag);
    }
}
