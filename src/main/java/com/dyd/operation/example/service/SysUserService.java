package com.dyd.operation.example.service;

import com.dyd.operation.example.entity.SysUser;

/**
 * @author 林金汉
 * @ClassName: SysUserService
 * @Description:
 * @date 2019/12/9 14:13
 */
public interface SysUserService {

    /**
     * 根据登录名查询用户
     *
     * @param username
     * @return com.dyd.operation.example.entity.SysUser
     * @author Kinhan_Lin
     * @date 2019/12/9
     */
    SysUser findByUsername(String username);

}
