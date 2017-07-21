package com.ffwb.controller;

import com.ffwb.DTO.AnswerDTO;
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
import java.util.Set;

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
                                                  @RequestParam(value = "type", defaultValue = "")int type,
                                                  @RequestParam(value="pageIndex", defaultValue="0") int pageIndex,
                                                  @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                                                  @RequestParam(value="sortField", defaultValue="id") String sortField,
                                                  @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){
        //TODO
        PageListModel questions = questionService.getQuestionsByConditions(label, type, pageIndex, pageSize, sortField, sortOrder);
        return ServiceResult.success(questions);
    }

//    @RequestMapping(value="/questions/label",method=RequestMethod.PUT)
//    @ResponseBody
//    public ServiceResult labelQuestion(@RequestBody List<QuestionDTO> dtolist)throws Exception{
//        checkParameter(dtolist!=null,"question list cannot be null");
//        boolean flag=questionService.labelQuestions(dtolist);
//        return ServiceResult.success("修改标签结果"+flag);
//    }

    /**
     * 问题更新
     * @param dtolist
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/questions/update",method=RequestMethod.PUT)
    @ResponseBody
    public ServiceResult updateQuestion(@RequestBody List<QuestionDTO> dtolist)throws Exception{
        checkParameter(dtolist!=null,"question to be updated is null");
        int time=questionService.updateQuestions(dtolist);
        return ServiceResult.success("成功更新"+time +"个问题,失败"+(dtolist.size()-time)+"次");
    }

    /**
     * 问题新增
     * @param dtolist
     * @param managerId
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/questions/add",method=RequestMethod.POST)
    @ResponseBody
    public ServiceResult addQuestion(@RequestBody List<QuestionDTO> dtolist, @RequestParam(value="managerId",defaultValue = "-1")Long managerId)throws Exception{
        checkParameter(dtolist!=null,"question to be added is null");
        int time=questionService.addQuestions(dtolist,managerId);
        return ServiceResult.success("成功增加"+time +"个问题,失败"+(dtolist.size()-time)+"次");
    }

    /**
     * 问题删除
     * @param dtolist
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/questions/delete",method=RequestMethod.PUT)
    @ResponseBody
    public ServiceResult deleteQuestion(@RequestBody List<QuestionDTO> dtolist)throws Exception{
        checkParameter(dtolist!=null,"question to be deleted is null");
        int time=questionService.deleteQuestions(dtolist);
        return ServiceResult.success("成功删除"+time +"个问题,失败"+(dtolist.size()-time)+"次");
    }

    /**
     * 转化题目答案
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/questions/convert",method=RequestMethod.PUT)
    @ResponseBody
    public ServiceResult convertQuestion()throws Exception{
        questionService.convertSolution();
        //questionService.addJudgeQuestion();
        return ServiceResult.success(null);
    }

    /**
     * 添加判断题
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/questions/addJudgeQuestion",method=RequestMethod.POST)
    @ResponseBody
    public ServiceResult addJudgeQuestion() throws IOException {
        questionService.addJudgeQuestion();
        return ServiceResult.success(null);
    }

    /**
     * 给问题打标签
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/question/tag",method=RequestMethod.PUT)
    @ResponseBody
    public ServiceResult tagQuestion(@RequestBody List<QuestionDTO> dtoList)throws Exception{
        checkParameter(dtoList!=null,"question is null");
        int count=questionService.tagQuestion(dtoList);
        if(count==0)
            return ServiceResult.fail("没有成功添加标签，可能是问题不包含关键词");
        else return ServiceResult.success("成功添加"+count+"条问题的标签");

    }

    /**
     * 得到答案及解答
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/question/analysis",method=RequestMethod.GET)
    @ResponseBody
    public ServiceResult getSolutions(@RequestParam (value = "answerId",defaultValue = "-1")Long answerId)throws Exception{
        AnswerDTO answerDTO=questionService.getAnsAndSol(answerId);
        if(answerDTO==null)
            return ServiceResult.fail("没有相关答案");
        else return ServiceResult.success(answerDTO);
    }
}
