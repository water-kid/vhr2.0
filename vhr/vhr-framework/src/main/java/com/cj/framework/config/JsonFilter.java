package com.cj.framework.config;

import com.cj.framework.entity.Hr;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

/**
 * 登录传递json
 */
public class JsonFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String contentType = request.getContentType();
        if (contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE) || contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE)){
            // 前端传入的是json
            try {
                // 通过io流，去解析请求体参数，，，比如：文件，json，，，   key-value也可以通过io流获取
                Hr hr = new ObjectMapper().readValue(request.getInputStream(), Hr.class);

                String username = hr.getUsername();
                String password = hr.getPassword();
                UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
                        password);
                // Allow subclasses to set the "details" property
                setDetails(request, authRequest);
                // 获取认证管理器去认证
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            //  key-value
            return super.attemptAuthentication(request,response);
        }




    }
}
