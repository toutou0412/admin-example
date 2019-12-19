package com.dyd.operation.example.entity;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author 林金汉
 * @ClassName: SysRoleResource
 * @Description:
 * @date 2019/12/9 13:52
 */
@Data
@Entity
@ToString
public class SysRoleResource {

    @Id
    @KeySql(useGeneratedKeys = true, dialect = IdentityDialect.MYSQL)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer roleId;

    private Integer resourceId;

}
