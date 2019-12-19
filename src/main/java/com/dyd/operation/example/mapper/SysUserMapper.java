package com.dyd.operation.example.mapper;

import com.dyd.operation.example.entity.SysUser;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author 林金汉
 * @ClassName: SysUserMapper
 * @Description:
 * @date 2019/12/9 14:14
 */
public interface SysUserMapper extends Mapper<SysUser>, MySqlMapper<SysUser> {
}
