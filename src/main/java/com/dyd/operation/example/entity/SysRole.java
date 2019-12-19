package com.dyd.operation.example.entity;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 林金汉
 * @ClassName: SysRole
 * @Description:
 * @date 2019/12/9 13:44
 */
@Data
@Entity
@ToString
public class SysRole {

    @Id
    @KeySql(useGeneratedKeys = true, dialect = IdentityDialect.MYSQL)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String desc;

    private Date subTime;

    private Integer subUserId;

    private Date updateTime;

    private Integer updateUserId;

}
