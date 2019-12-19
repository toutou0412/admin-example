package com.dyd.operation.example.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author 林金汉
 * @ClassName: PageResponseBo
 * @Description:
 * @date 2019/12/11 18:26
 */
@Data
@ToString
@AllArgsConstructor
public class PageResponseBo<T> {

    /**
     * 状态码,0为成功
     */
    private Integer code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 总条数
     */
    private Integer count;

    /**
     * 数据
     */
    private List<T> data;

}
