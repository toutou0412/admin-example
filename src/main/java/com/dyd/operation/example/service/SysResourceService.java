package com.dyd.operation.example.service;

import com.dyd.operation.example.bo.MenuBo;
import com.dyd.operation.example.entity.SysResource;

import java.util.List;

/**
 * @author 林金汉
 * @ClassName: SysResourceService
 * @Description:
 * @date 2019/12/9 14:13
 */
public interface SysResourceService {

    /**
     * 根据用户ID获取资源列表
     *
     * @param userId
     * @return java.util.List<com.dyd.operation.example.entity.SysResource>
     * @author Kinhan_Lin
     * @date 2019/12/9
     */
    List<SysResource> findByUserId(Integer userId);

    /**
     * 根据用户ID获取可访问菜单列表
     *
     * @param userId
     * @return java.util.List<com.dyd.operation.example.bo.MenuBo>
     * @author Kinhan_Lin
     * @date 2019/12/10
     */
    List<MenuBo> getMenuList(Integer userId);
}
