package com.dyd.operation.example.common.utils;

import com.dyd.operation.example.auth.UserDetailsImpl;
import com.dyd.operation.example.entity.SysUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

/**
 * @author 林金汉
 * @ClassName: UserUtil
 * @Description:
 * @date 2019/10/29 17:21
 */
public class UserUtil {

    public static UserDetailsImpl getUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return new UserDetailsImpl(new SysUser(), new ArrayList<>(), new ArrayList<>());
        }

        boolean logined = authentication.isAuthenticated();
        if (!logined){
            return new UserDetailsImpl(new SysUser(), new ArrayList<>(), new ArrayList<>());
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails;
    }

}
