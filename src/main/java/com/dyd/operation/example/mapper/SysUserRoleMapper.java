package com.dyd.operation.example.mapper;

import com.dyd.operation.example.entity.SysUserRole;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author 林金汉
 * @ClassName: SysUserRoleMapper
 * @Description:
 * @date 2019/12/9 14:17
 */
public interface SysUserRoleMapper extends Mapper<SysUserRole>, MySqlMapper<SysUserRole> {
}
