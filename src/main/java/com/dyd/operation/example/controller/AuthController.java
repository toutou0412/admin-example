package com.dyd.operation.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 林金汉
 * @ClassName: AuthController
 * @Description:
 * @date 2019/12/9 15:58
 */
@Controller
public class AuthController {

    @GetMapping("login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView("login.html");
        return modelAndView;
    }

}
