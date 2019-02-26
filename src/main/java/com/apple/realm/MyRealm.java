package com.apple.realm;

import com.apple.model.Blogger;
import com.apple.service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @program: Blog
 * @description:
 * @author: Apple
 * @create: 2019-02-12 10:54
 **/
public class MyRealm extends AuthorizingRealm {

    @Resource
    private BloggerService bloggerService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 身份认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String  userName = (String) token.getPrincipal();
        Blogger blogger  = bloggerService.getByUserName(userName);
        if (blogger != null) {
            SecurityUtils.getSubject().getSession().setAttribute("currentUser", blogger);
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(blogger.getUserName(), blogger.getPassword(), "xxx");
            return authenticationInfo;

        } else {
            return null;
        }
    }
}


