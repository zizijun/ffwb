package com.ffwb.controller;


import com.ffwb.DTO.SkillModel;
import com.ffwb.model.ServiceResult;
import com.ffwb.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by jinchuyang on 2017/7/23.
 */
@Controller
public class SkillController extends ApiController{
    @Autowired
    private SkillService skillService;

    /**
     * 获取大类的技能分析
     * @param id
     * @return
     */
    @RequestMapping(value = "/skillmodel/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getBig (@PathVariable(value = "id") long id){
         List<SkillModel> skillModel = skillService.getSkillModel(id);
        return ServiceResult.success(skillModel);
    }

    @RequestMapping(value = "/skillmodel/total/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getAll (@PathVariable(value = "id") long id){
        List<SkillModel> skillModel = skillService.getTotalSkillModel(id);
        return ServiceResult.success(skillModel);
    }

    @RequestMapping(value = "/skillmodel/random", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult addSuiji (){
        skillService.createRandomData();
        return ServiceResult.success(null);
    }
}
