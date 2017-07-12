package com.ffwb.controller;

import com.ffwb.model.ServiceResult;
import com.ffwb.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jinchuyang on 2017/7/12.
 */
@Controller
public class TagController extends ApiController{

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getAll (){
        return ServiceResult.success(tagService.findAllAlive(1));
    }
}
