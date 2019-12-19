package com.dyd.operation.example.bo;

import lombok.Data;
import lombok.ToString;

/**
 * @author 林金汉
 * @ClassName: PageRequestBo
 * @Description: 分页请求Bo，最低分页条数为10
 * @date 2019/12/11 18:32
 */
@Data
@ToString
public class PageRequestBo {

    public PageRequestBo(){
        this.page = 1;
        this.limit = 10;
    }

    public PageRequestBo(Integer page, Integer limit){
        if (page == null || page < 1){
            page = 1;
        }

        if (limit == null || limit < 10){
            limit = 10;
        }

        this.page = page;
        this.limit = limit;
    }

    /**
     * 当前页数
     */
    private Integer page;

    /**
     * 每页大小
     */
    private Integer limit;

}
