package com.dyd.operation.example.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 林金汉
 * @ClassName: SysUser
 * @Description:
 * @date 2019/10/28 15:14
 */
@Data
@Entity
@ToString
public class SysUser implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true, dialect = IdentityDialect.MYSQL)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String realName;

    private Integer sex;

    private String phone;

    /**
     * 0或null：未锁定，1：锁定
     */
    private Integer lockStatus;

    /**
     * 0:未删除，1：已删除
     */
    private Integer delStatus;

    private Date lastLoginTime;

    private Integer subUserId;

    private Date updateTime;

    private Integer updateUserId;

    public void setEncodePassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(password);
        this.password = encodePasswd;
    }}
