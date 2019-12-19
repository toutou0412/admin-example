package com.dyd.operation.example.controller.sys;

import com.dyd.operation.example.bo.PageRequestBo;
import com.dyd.operation.example.bo.PageResponseBo;
import com.dyd.operation.example.bo.ResponseBodyBo;
import com.dyd.operation.example.common.enums.ActionType;
import com.dyd.operation.example.entity.SysRole;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 林金汉
 * @ClassName: SysRoleRole
 * @Description:
 * @date 2019/12/15 12:16
 */
@Controller
@RequestMapping("sys/role")
public class SysRoleRole {

    private List<SysRole> list = new ArrayList<>();

    {
        for (int i = 0; i < 31; i++) {
            SysRole sysRole = new SysRole();
            sysRole.setId(i + 1);
            sysRole.setName("name-" + i);
            sysRole.setDesc("desc-" + i);
            list.add(sysRole);
        }
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public ModelAndView list(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("module/sys/role/list.html");

        return modelAndView;
    }

    @GetMapping("listData")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:list')")
    public PageResponseBo listData(PageRequestBo pageRequestBo, SysRole search){

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
    @PreAuthorize("hasAuthority('sys:role:info')")
    public ModelAndView dataView(Integer action, Integer id){


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("module/sys/role/dataView.html");
        modelAndView.addObject("action", action);
        // 新增
        if (ActionType.ADD.getKey().equals(action)){
            return modelAndView;
        }

        SysRole sysRole = list.get(id - 1);
        modelAndView.addObject("entity", sysRole);

        return modelAndView;
    }

    @PutMapping("add")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:add')")
    public ResponseBodyBo add(@RequestBody SysRole sysRole){
        String msg = validate(sysRole);

        if (StringUtils.isNotBlank(msg)){
            return ResponseBodyBo.error(msg);
        }

        int i = list.get(list.size() - 1).getId();
        sysRole.setId(i + 1);
        list.add(sysRole);

        return ResponseBodyBo.ok();
    }

    @PostMapping("update")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:update')")
    public ResponseBodyBo update(@RequestBody SysRole sysRole){
        String msg = validate(sysRole);

        if (sysRole.getId() == null){
            msg = "ID不能为空";
        }

        if (StringUtils.isNotBlank(msg)){
            return ResponseBodyBo.error(msg);
        }

        list.set(sysRole.getId() - 1, sysRole);

        return ResponseBodyBo.ok();
    }

    @DeleteMapping("delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public ResponseBodyBo deletByIds(Integer[] ids){

        if(ArrayUtils.contains(ids, 1)){
            return ResponseBodyBo.error("系统管理员不能删除");
        }

        return ResponseBodyBo.ok();
    }

    private String validate(SysRole sysRole) {

        if (StringUtils.isBlank(sysRole.getName())){
            return "名称不能为空";
        }

        return null;
    }

}
