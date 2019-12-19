package com.dyd.operation.example.mapper;

import com.dyd.operation.example.entity.SysRoleResource;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author 林金汉
 * @ClassName: SysRoleResourceMapper
 * @Description:
 * @date 2019/12/9 14:17
 */
public interface SysRoleResourceMapper extends Mapper<SysRoleResource>
        , IdsMapper<SysRoleResource>
        , MySqlMapper<SysRoleResource> {
}
