package com.code.resumemanagement.realm;


import com.code.resumemanagement.domain.User;
import com.code.resumemanagement.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;


public class AuthRealm  extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(AuthRealm.class);
    @Autowired
    private IUserService userService;
    /**
     * 用户授权认证
     */
    /*@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("======用户授权认证======");
        String userName = principalCollection.getPrimaryPrincipal().toString();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(userService.queryRolesByName(userName));
        return simpleAuthorizationInfo;
    }*/




    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //根据自己系统规则的需要编写获取授权信息，这里为了快速入门只获取了用户对应角色的资源url信息
        logger.info("======用户授权认证======");
        Set<String> names=   principalCollection.getRealmNames();
        String userName="";
        if(names!=null&&names.size()==1){
            for(String item:names){
                userName=item;

            }
        }else{
            return null;
        }
       // String userName = (String) principalCollection.fromRealm(getName()).iterator().next();
        //  String userName = principalCollection.getPrimaryPrincipal().toString();
        if (userName != null) {
            Set<String> pers = userService.selectPermissionsByUserName(userName);
            if (pers != null && !pers.isEmpty()) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                for (String each : pers) {
                    //将权限资源添加到用户信息中
                    info.addStringPermission(each);
                }
                return info;
            }
        }
        return null;
    }


    /**
     * 用户登陆认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("======用户登陆认证======");
        String userName = authenticationToken.getPrincipal().toString();
        User user = userService.queryUserByName(userName);
        if (user!=null) {
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassWord(), user.getUserName());
            return authenticationInfo;
        }
        return null;
    }


}
