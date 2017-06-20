package com.ffwb.controller;

import com.ffwb.entity.Manager;
import com.ffwb.model.ServiceResult;
import com.ffwb.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jinchuyang on 16/10/19.
 */
@Controller
public class ManagerController extends ApiController{
    @Autowired
    private ManagerService managerService;

    /**
     * 用户登录
     * @param manager
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/manager/login", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult login(@RequestBody Manager manager) throws Exception{
        checkParameter(manager!=null,"manager cannot be empty!");
        //checkParameter(manager.getTel()!=null,"tel cannot be empty!");
        Manager managerGet = managerService.findByTelAndPassordAndAlive(manager.getTel(), manager.getPassword());
        return ServiceResult.success(managerGet);
    }

    @RequestMapping(value = "/manager/register", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult register(@RequestBody Manager manager) throws Exception{
        checkParameter(manager!=null,"manager cannot be empty!");
        Manager managerPost = managerService.addManager(manager);
        return ServiceResult.success(manager);
    }
}
