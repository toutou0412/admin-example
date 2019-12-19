package com.dyd.operation.example.controller;

import com.dyd.operation.example.auth.UserDetailsImpl;
import com.dyd.operation.example.common.utils.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 林金汉
 * @ClassName: IndexController
 * @Description:
 * @date 2019/12/9 15:55
 */
@Controller
public class IndexController {

    /**
     * 首页
     *
     * @return
     */
    @GetMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");

        UserDetailsImpl userDetails = UserUtil.getUserDetails();
        modelAndView.addObject("menuList", userDetails.getMenuList());

        modelAndView.addObject("user", userDetails);
        return modelAndView;
    }

}
