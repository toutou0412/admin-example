package com.dyd.operation.example.service;

import com.dyd.operation.example.entity.SysRole;

import java.util.List;

/**
 * @author 林金汉
 * @ClassName: SysRoleService
 * @Description:
 * @date 2019/12/9 14:13
 */
public interface SysRoleService {
    List<SysRole> findByUserId(Integer id);
}
