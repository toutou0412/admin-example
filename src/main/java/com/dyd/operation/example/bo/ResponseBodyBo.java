package com.dyd.operation.example.bo;

import com.dyd.operation.example.common.enums.ResCode;
import lombok.Data;

import java.util.Map;

/**
 * @author 林金汉
 * @ClassName: ResponseBodyBo
 * @Description:
 * @date 2019/12/12 13:20
 */
@Data
public class ResponseBodyBo {

    /**
     * 状态码,0为成功
     */
    private Integer code;

    /**
     * 信息
     */
    private String msg;

    private Map<String, Object> data;

    public ResponseBodyBo(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ResponseBodyBo(ResCode resCode){
        this.code = resCode.getKey();
        this.msg = resCode.getValue();
    }

    public static ResponseBodyBo ok(){
        return new ResponseBodyBo(ResCode.OK);
    }

    public static ResponseBodyBo error(String msg){
        return new ResponseBodyBo(500, msg);
    }

}
