package com.dyd.operation.example.entity;

import lombok.Data;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 林金汉
 * @ClassName: Role
 * @Description:
 * @date 2019/10/31 15:04
 */
@Data
@Entity
@ToString
public class SysResource implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true, dialect = IdentityDialect.MYSQL)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父级菜单ID，一级菜单为0
     */
    private Integer parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * url
     */
    private String url;

    /**
     * 权限码
     */
    private String code;

    /**
     * 类型，0：目录，1：菜单，2：按钮
     */
    private Integer type;

    /**
     * 菜单图标，icon-font
     */
    private String icon;

    /**
     * 菜单排序
     */
    private Integer orderNum;

    private Date subTime;

    private Integer subUserId;

    private Date updateTime;

    private Integer updateUserId;

}
