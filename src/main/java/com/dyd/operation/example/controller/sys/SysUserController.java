package com.dyd.operation.example.controller.sys;

import com.dyd.operation.example.auth.UserDetailsImpl;
import com.dyd.operation.example.bo.PageRequestBo;
import com.dyd.operation.example.bo.PageResponseBo;
import com.dyd.operation.example.bo.ResponseBodyBo;
import com.dyd.operation.example.bo.SetPasswordBo;
import com.dyd.operation.example.common.enums.ActionType;
import com.dyd.operation.example.common.utils.UserUtil;
import com.dyd.operation.example.entity.SysUser;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 林金汉
 * @ClassName: SysUserController
 * @Description:
 * @date 2019/12/11 15:47
 */
@Controller
@RequestMapping("sys/user")
public class SysUserController {

    private List<SysUser> list = new ArrayList<>();

    {
        for (int i = 0; i < 31; i++) {
            SysUser user = new SysUser();
            user.setId(i + 1);
            user.setUsername("username-" + i);
            user.setEncodePassword("123456");
            user.setPhone("13112341234");
            user.setRealName("realName-" + i);
            user.setEmail("123@qq.com");
            user.setSex(i % 2 == 0 ? 1 : 2);
            list.add(user);
        }
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public ModelAndView list(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("module/sys/user/list.html");

        return modelAndView;
    }

    @GetMapping("listData")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:list')")
    public PageResponseBo listData(PageRequestBo pageRequestBo, SysUser search){

        int offset = (pageRequestBo.getPage() - 1) * pageRequestBo.getLimit() - 1;
        if (offset < 0){
            offset = 0;
        }
        int top = offset + pageRequestBo.getLimit();
        if (top > list.size()){
            top = list.size() - 1;
        }

        return new PageResponseBo(0, "", list.size(), list.subList(offset, top));
    }

    @GetMapping("dataView")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public ModelAndView dataView(Integer action, Integer id){


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("module/sys/user/dataView.html");
        modelAndView.addObject("action", action);
        // 新增
        if (ActionType.ADD.getKey().equals(action)){
            return modelAndView;
        }

        SysUser user = list.get(id - 1);
        modelAndView.addObject("entity", user);

        return modelAndView;
    }

    @PutMapping("add")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:add')")
    public ResponseBodyBo add(@RequestBody SysUser user){
        String msg = validate(user);

        if (StringUtils.isNotBlank(msg)){
            return ResponseBodyBo.error(msg);
        }

        int i = list.get(list.size() - 1).getId();
        user.setId(i + 1);
        user.setUsername("username-" + i);
        user.setEncodePassword("123456");
        user.setPhone("13112341234");
        user.setRealName("realName-" + i);
        user.setEmail("123@qq.com");
        user.setSex(i % 2 == 0 ? 1 : 2);
        list.add(user);

        return ResponseBodyBo.ok();
    }

    @PostMapping("update")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:update')")
    public ResponseBodyBo update(@RequestBody SysUser user){
        String msg = validate(user);

        if (user.getId() == null){
            msg = "ID不能为空";
        }

        if (StringUtils.isNotBlank(msg)){
            return ResponseBodyBo.error(msg);
        }

        list.set(user.getId() - 1, user);

        return ResponseBodyBo.ok();
    }

    private String validate(SysUser user) {

        if (StringUtils.isBlank(user.getPhone())){
            return "手机号不能为空";
        }

        return null;
    }

    @DeleteMapping("delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public ResponseBodyBo deletByIds(Integer[] ids){

        if(ArrayUtils.contains(ids, 1)){
            return ResponseBodyBo.error("系统管理员不能删除");
        }

        return ResponseBodyBo.ok();
    }

    @GetMapping("password")
    public ModelAndView password(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("module/sys/user/password.html");

        return modelAndView;
    }

    @PostMapping("setPassword")
    @ResponseBody
    public ResponseBodyBo setPassword(@RequestBody SetPasswordBo setPasswordBo){

        if (setPasswordBo == null
                || StringUtils.isBlank(setPasswordBo.getOldPassword())
                || StringUtils.isBlank(setPasswordBo.getPassword())
                || StringUtils.isBlank(setPasswordBo.getRepassword())
                || !setPasswordBo.getPassword().equals(setPasswordBo.getRepassword())
        ){
                return ResponseBodyBo.error("请求参数有误");
        }

        // 验证当前密码
        UserDetailsImpl userDetails = UserUtil.getUserDetails();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean oldPwdCorrect = passwordEncoder.matches(setPasswordBo.getOldPassword(), userDetails.getPassword());
        if (!oldPwdCorrect){
            return ResponseBodyBo.error("当前密码错误");
        }

        // 密码正确,修改密码
        SysUser user = new SysUser();
        user.setId(userDetails.getUser().getId());
        user.setEncodePassword(setPasswordBo.getPassword());

        // 根据ID更新密码

        return ResponseBodyBo.ok();
    }

}
