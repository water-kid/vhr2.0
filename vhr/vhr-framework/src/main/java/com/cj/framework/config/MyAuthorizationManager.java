package com.cj.framework.config;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

public class MyAuthorizationManager implements AuthorizationManager<Object> {

    @Override
    public void verify(Supplier<Authentication> authentication, Object object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    /**
     * 判断这个请求，，是否具备权限
     * @param authentication the {@link Supplier} of the {@link Authentication} to check  当前登录的对象
     * @param object the {@link T} object to check    request请求
     * @return
     */

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, Object object) {

//        authentication

        return null;
    }
}
