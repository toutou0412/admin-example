package com.dyd.operation.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 林金汉
 * @ClassName: SecurityConfig
 * @Description:
 * @date 2019/10/31 16:28
 */
@Configuration
// 表示开启全局方法注解，可在指定方法上面添加注解指定权限，需含有指定权限才可调用(基于表达式的权限控制)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 自定义userDetails
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    UserDetailsService customUserService() {
        return userDetailsService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 BCrypt 加密
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserService());
        // 设置密码加密方式
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 请根据自身业务进行扩展
                // 都可以访问
        http.authorizeRequests()
                .antMatchers(
                        HttpMethod.GET
                        ,"/"
                        ,"/favicon.ico"
                        ,"/**/*.css"
                        ,"/**/*.js"
                        ,"/**/*.ttf"
                        ,"/**/*.woff"
                        ,"/**/layui/**"
                        ,"/login"
                        ,"/40*"
                        ,"/50*"
                ).permitAll()
                // 其余所有接口需要登录才能访问
                .anyRequest().authenticated()
                // 基于 Form 表单登录验证
                .and().formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error=true")
                        .permitAll()
                // 默认登陆成功页
                .defaultSuccessUrl("/index")
                .and().logout().logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/")
                // 关闭CSRF跨域
                .and().csrf().disable()
        ;
        // 记住我
        http.rememberMe().rememberMeParameter("remember")
                .userDetailsService(userDetailsService).tokenValiditySeconds(3600);
        // 同源
        http.headers().frameOptions().sameOrigin();
    }

    /**
     * 认证信息管理
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService());
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // remove the ROLE_ prefix
        return new GrantedAuthorityDefaults("");
    }

}
