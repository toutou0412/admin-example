package com.dyd.operation.example.service.impl;

import com.dyd.operation.example.entity.SysUser;
import com.dyd.operation.example.mapper.SysUserMapper;
import com.dyd.operation.example.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林金汉
 * @ClassName: SysUserServiceImpl
 * @Description:
 * @date 2019/12/9 14:13
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findByUsername(String username) {

        SysUser search = new SysUser();
        search.setUsername(username);
        List<SysUser> list = sysUserMapper.select(search);
        if (list != null && list.size() > 0){
            return list.get(0);
        }

        return null;
    }

}
