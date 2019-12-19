package com.dyd.operation.example.controller.sys;

import com.dyd.operation.example.bo.CopyTemplateBo;
import com.dyd.operation.example.bo.PageRequestBo;
import com.dyd.operation.example.bo.PageResponseBo;
import com.dyd.operation.example.bo.ResponseBodyBo;
import com.dyd.operation.example.common.enums.ActionType;
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
 * @ClassName: CopyTemplateController
 * @Description: 表格模版:
 * 需要替换：1、sys/copy   ===>   自定义路径
 * 需要替换：2、sys:copy   ===>   自定义权限code
 * 需要替换：3、CopyTemplateBo   ===>   自定义请求Bo或响应Bo
 * 需要重写：4、validata方法   ===>   自定义参数合法性校验
 * @date 2019/12/15 16:48
 */
@Controller
@RequestMapping("sys/copy")
public class CopyController {

    private List<CopyTemplateBo> list = new ArrayList<>();

    {
        for (int i = 0; i < 31; i++) {
            CopyTemplateBo copyTemplateBo = new CopyTemplateBo();
            copyTemplateBo.setId(i + 1);
            copyTemplateBo.setName("name-" + i);
            list.add(copyTemplateBo);
        }
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:copy:list')")
    public ModelAndView list(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("module/sys/copy/list.html");

        return modelAndView;
    }

    @GetMapping("listData")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:copy:list')")
    public PageResponseBo listData(PageRequestBo pageRequestBo, CopyTemplateBo search){

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
    @PreAuthorize("hasAuthority('sys:copy:info')")
    public ModelAndView dataView(Integer action, Integer id){


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("module/sys/copy/dataView.html");
        modelAndView.addObject("action", action);
        // 新增
        if (ActionType.ADD.getKey().equals(action)){
            return modelAndView;
        }

        CopyTemplateBo copyTemplateBo = list.get(id - 1);
        modelAndView.addObject("entity", copyTemplateBo);

        return modelAndView;
    }

    @PutMapping("add")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:copy:add')")
    public ResponseBodyBo add(@RequestBody CopyTemplateBo copyTemplateBo){
        String msg = validate(copyTemplateBo);

        if (StringUtils.isNotBlank(msg)){
            return ResponseBodyBo.error(msg);
        }

        int i = list.get(list.size() - 1).getId();
        copyTemplateBo.setId(i + 1);
        list.add(copyTemplateBo);

        return ResponseBodyBo.ok();
    }

    @PostMapping("update")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:copy:update')")
    public ResponseBodyBo update(@RequestBody CopyTemplateBo copyTemplateBo){
        String msg = validate(copyTemplateBo);

        if (copyTemplateBo.getId() == null){
            msg = "ID不能为空";
        }

        if (StringUtils.isNotBlank(msg)){
            return ResponseBodyBo.error(msg);
        }

        list.set(copyTemplateBo.getId() - 1, copyTemplateBo);

        return ResponseBodyBo.ok();
    }

    @DeleteMapping("delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:copy:delete')")
    public ResponseBodyBo deletByIds(Integer[] ids){

        if(ArrayUtils.contains(ids, 1)){
            return ResponseBodyBo.error("系统管理员不能删除");
        }

        return ResponseBodyBo.ok();
    }

    private String validate(CopyTemplateBo copyTemplateBo) {

        if (StringUtils.isBlank(copyTemplateBo.getName())){
            return "名称不能为空";
        }

        return null;
    }

}
