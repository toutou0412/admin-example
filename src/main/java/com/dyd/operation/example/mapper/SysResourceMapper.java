package com.dyd.operation.example.mapper;

import com.dyd.operation.example.entity.SysResource;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author 林金汉
 * @ClassName: SysResourceMapper
 * @Description:
 * @date 2019/12/9 14:16
 */
public interface SysResourceMapper extends Mapper<SysResource>
        , IdsMapper<SysResource>
        , MySqlMapper<SysResource> {

    /**
     * 根据userId获取资源列表
     *
     * @param userId
     * @return java.util.List<com.dyd.operation.example.entity.SysResource>
     * @author Kinhan_Lin
     * @date 2019/12/9
     */
    List<SysResource> findByUserId(Integer userId);

}
