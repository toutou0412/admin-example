package com.dyd.operation.example.auth;

import com.dyd.operation.example.bo.MenuBo;
import com.dyd.operation.example.entity.SysResource;
import com.dyd.operation.example.entity.SysUser;
import com.dyd.operation.example.service.SysResourceService;
import com.dyd.operation.example.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 林金汉
 * @ClassName: UserDetailsService
 * @Description:
 * @date 2019/10/31 14:57
 */
@Service("userDetailsService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    /**
     * 用户操作服务
     */
    @Autowired
    private SysUserService userService;

    /**
     * 用户角色服务
     */
    @Autowired
    private SysResourceService resourceService;

    /**
     * 根据用户登录名定位用户。
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = null;
        try {
            if (StringUtils.isBlank(username)) {
                throw new UsernameNotFoundException("请输入用户名！");
            }
            SysUser user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("该用户不存在！");
            } else {
                // 查询当前用户的权限
                List<SysResource> resourceList = resourceService.findByUserId(user.getId());
                Set authoritiesSet = new HashSet();
                for (SysResource resource : resourceList) {
                    SimpleGrantedAuthority grant = new SimpleGrantedAuthority(resource.getCode());
                    authoritiesSet.add(grant);
                }
                // 查询当前用户的可访问菜单
                List<MenuBo> menuList = resourceService.getMenuList(user.getId());
                //封装自定义UserDetails类
                userDetails = new UserDetailsImpl(user, menuList, authoritiesSet);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return userDetails;
    }

}
