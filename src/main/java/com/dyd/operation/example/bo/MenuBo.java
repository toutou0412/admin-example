package com.dyd.operation.example.bo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author 林金汉
 * @ClassName: MenuBo
 * @Description:
 * @date 2019/12/10 16:15
 */
@Data
@ToString
public class MenuBo {

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

    /**
     * 子菜单列表
     */
    private List<MenuBo> childList;

}
