package com.dyd.operation.example.common.utils.password;

public class Password {

    public static String encrypt(String str) {
        // 小于15位的密码做处理
        if (str != null && str.length() < 15) {
            Md5PwdEncoder m = new Md5PwdEncoder();
            str = m.encodePassword(str);
            return str;
        } else {
            return str;
        }
    }

}
