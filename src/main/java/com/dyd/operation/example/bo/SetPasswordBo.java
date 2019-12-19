package com.dyd.operation.example.bo;

import lombok.Data;
import lombok.ToString;

/**
 * @author 林金汉
 * @ClassName: SetPasswordBo
 * @Description: 修改密码请求Bo
 * @date 2019/12/13 15:14
 */
@Data
@ToString
public class SetPasswordBo {

    private String oldPassword;

    private String password;

    private String repassword;

}
