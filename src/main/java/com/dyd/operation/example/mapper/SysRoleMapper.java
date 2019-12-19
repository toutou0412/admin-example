package com.dyd.operation.example.mapper;

import com.dyd.operation.example.entity.SysRole;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author 林金汉
 * @ClassName: SysRoleMapper
 * @Description:
 * @date 2019/12/9 14:15
 */
public interface SysRoleMapper extends Mapper<SysRole>, MySqlMapper<SysRole> {
}
