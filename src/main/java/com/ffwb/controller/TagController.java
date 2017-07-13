package com.ffwb.controller;

import com.ffwb.entity.Tag;
import com.ffwb.model.ServiceResult;
import com.ffwb.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by jinchuyang on 2017/7/12.
 */
@Controller
public class TagController extends ApiController{

    @Autowired
    private TagService tagService;

    /**
     * 获取所有的标签
     * @return
     */
    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getAll (){
        List<Tag> tags = tagService.findAllAlive(1);
        return ServiceResult.success(tags);
    }

    /**
     * 新增标签
     * @return
     */
    @RequestMapping(value = "/tag/add", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult add (@RequestBody Tag tag){
        checkParameter(tag!=null,"content is null");
        Tag newTag = tagService.addTag(tag);
        if (newTag.getId() != -1){
            return ServiceResult.success("添加成功");
        }else {
            return ServiceResult.fail("添加失败");
        }

    }
}
