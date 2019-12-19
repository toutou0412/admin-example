package com.dyd.operation.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 林金汉
 * @ClassName: TestController
 * @Description: 测试类
 * @date 2019/12/9 10:23
 */
@Controller
public class TestController {

    @GetMapping("test")
    @ResponseBody
    @PreAuthorize("hasAuthority('role1-resource1')")
    public String list() {
        return "sssssssssss";
    }

    @GetMapping("charts")
    public ModelAndView chart(){
        return new ModelAndView("/module/test/charts.html");
    }

}
