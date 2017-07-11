package com.ffwb.controller;

import com.ffwb.entity.User;
import com.ffwb.model.ServiceResult;
import com.ffwb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jinchuyang on 2017/6/20.
 */
@Controller
public class UserController extends ApiController{
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult register(@RequestBody User user) throws Exception{
        checkParameter(user!=null,"user cannot be empty!");
        User newUser = userService.addUser(user);
        return ServiceResult.success(newUser);
    }

    /**
     * 用户登陆
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult login(@RequestBody User user) throws Exception{
        checkParameter(user!=null && user.getTel()!=null,"user cannot be empty!");
        User newUser = userService.login(user.getTel());
        return ServiceResult.success(newUser);
    }
}
