package com.dyd.operation.example.service.impl;

import com.dyd.operation.example.bo.MenuBo;
import com.dyd.operation.example.common.GlobalConstants;
import com.dyd.operation.example.entity.SysResource;
import com.dyd.operation.example.entity.SysRoleResource;
import com.dyd.operation.example.entity.SysUserRole;
import com.dyd.operation.example.mapper.SysResourceMapper;
import com.dyd.operation.example.mapper.SysRoleResourceMapper;
import com.dyd.operation.example.mapper.SysUserRoleMapper;
import com.dyd.operation.example.service.SysResourceService;
import com.dyd.operation.example.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author 林金汉
 * @ClassName: SysUserServiceImpl
 * @Description:
 * @date 2019/12/9 14:13
 */
@Service
public class SysResourceServiceImpl implements SysResourceService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleResourceMapper roleResourceMapper;

    @Autowired
    private SysResourceMapper resourceMapper;

    @Override
    public List<SysResource> findByUserId(Integer userId) {

        // 超级管理员，返回所有资源
        if (GlobalConstants.ADMIN_ID.equals(userId)){
            return resourceMapper.selectAll();
        }

        List<SysResource> list = resourceMapper.findByUserId(userId);
        if (list != null && list.size() > 0){
            return list;
        }

        return new ArrayList<>();
    }

    @Override
    public List<MenuBo> getMenuList(Integer userId) {

        List<MenuBo> rtnList = new ArrayList<>();

        List<SysResource> list = this.findByUserId(userId);

        if (list != null && list.size() > 0) {

            for (SysResource resource : list) {
                //父节点是0的，为根节点。
                if(resource.getParentId() == null || resource.getParentId() == 0){
                    MenuBo menu = new MenuBo();
                    BeanUtils.copyProperties(resource, menu);
                    rtnList.add(menu);
                }
            }
            // 排序
            Collections.sort(rtnList, order());

            for (MenuBo nav : rtnList) {
                /* 获取根节点下的所有子节点 使用getChild方法*/
                List<MenuBo> childList = getChild(nav.getId(), list);
                // 给根节点设置子节点
                nav.setChildList(childList);
            }
        }
        return rtnList;
    }

    /**
     * 获取子节点
     * @param id 父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    private List<MenuBo> getChild(Integer id, List<SysResource> allMenu) {
        //子菜单
        List<MenuBo> childList = new ArrayList<MenuBo>();
        for (SysResource resource : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if(resource.getParentId().equals(id)){
                MenuBo menu = new MenuBo();
                BeanUtils.copyProperties(resource, menu);
                childList.add(menu);
            }
        }
        //递归
        for (MenuBo nav : childList) {
            nav.setChildList(getChild(nav.getId(), allMenu));
        }
        Collections.sort(childList,order());//排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return new ArrayList<MenuBo>();
        }
        return childList;
    }

    /*
     * 排序,根据orderNum排序
     */
    public Comparator<MenuBo> order(){
        Comparator<MenuBo> comparator = new Comparator<MenuBo>() {
            @Override
            public int compare(MenuBo o1, MenuBo o2) {
                if(o1.getOrderNum() != o2.getOrderNum()){
                    return o1.getOrderNum() - o2.getOrderNum();
                }
                return 0;
            }
        };
        return comparator;
    }

}
