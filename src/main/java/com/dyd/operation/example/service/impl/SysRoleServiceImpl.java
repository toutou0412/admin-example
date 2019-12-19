package com.dyd.operation.example.service.impl;

import com.dyd.operation.example.entity.SysRole;
import com.dyd.operation.example.service.SysRoleService;
import com.dyd.operation.example.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林金汉
 * @ClassName: SysUserServiceImpl
 * @Description:
 * @date 2019/12/9 14:13
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Override
    public List<SysRole> findByUserId(Integer id) {
        return null;
    }
}
