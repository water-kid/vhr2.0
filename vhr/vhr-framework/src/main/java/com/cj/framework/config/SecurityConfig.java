package com.cj.framework.config;

import com.cj.framework.entity.Hr;
import com.cj.framework.entity.RespBean;
import com.cj.framework.entity.Role;
import com.cj.framework.entity.vo.MenuWithRole;
import com.cj.framework.service.IHrService;
import com.cj.framework.service.IMenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

@Configuration
public class SecurityConfig {

    /**
     * 配置了  JsonFilter ,,,    httpsecurity 中的 fromLogin就失效了
     * @return
     */
    JsonFilter jsonFilter(){
        JsonFilter jsonFilter = new JsonFilter();
        jsonFilter.setFilterProcessesUrl("/login");
        jsonFilter.setAuthenticationSuccessHandler((req,resp,auth)->{
            resp.setContentType("application/json;charset=utf-8");
            Hr hr = (Hr) auth.getPrincipal();
            hr.setPassword(null);
            resp.getWriter().write(new ObjectMapper().writeValueAsString(RespBean.ok("登录成功",hr)));
        });
        jsonFilter.setAuthenticationFailureHandler((req,resp,e)->{
            resp.setContentType("application/json;charset=utf-8");
            RespBean error = RespBean.error("登录失败");

            if (e instanceof BadCredentialsException){
                error.setMessage("密码错误");
            }else if (e instanceof DisabledException){
                error.setMessage("用户被禁用");
            }else if (e instanceof LockedException){
                error.setMessage("账户被锁定");
            }else if (e instanceof AccountExpiredException){
                error.setMessage("账户过期");
            }else if(e instanceof CredentialsExpiredException){
                error.setMessage("密码过期");
            }

            resp.getWriter().write(new ObjectMapper().writeValueAsString(error));
        });


        // 需要设置自己的 AuthenticationManager
        jsonFilter.setAuthenticationManager(authenticationManager());

        /**
         *  每一次都会从 httpSession中获取用户，，如果httpsession中没有用户，就会表示成没有登录，，
         *  新配置的 filter 需要告知 ，，用户信息存放在哪里，，
         */

        // 自己配置的filter 需要设置 SecurityContextHolder 存储用户的位置
        jsonFilter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());

        return jsonFilter;
    }

    @Autowired
    IHrService hrService;

    /**
     * AuthenticationManager :
     *      实现类：   ProviderManager
     *      管理很多 provider
     * @return
     */
    @Bean
    AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(hrService);

        ProviderManager providerManager = new ProviderManager(daoAuthenticationProvider);
        return providerManager;
    }

    @Autowired
    IMenuService menuService;

//    @Autowired
//    AntPathMatcher antPathMatcher;
    @Bean
    public AntPathMatcher antPathMatcher(){
        return new AntPathMatcher();
    }


    /**
     * 过滤器
     *  : 配置过滤器链
     *  DispatchServlet
     *
     *  DefaultLoginPageGeneratingFilter : 默认登录页面过滤器
     *  DefaultLogoutPageGeneratingFilter ： 默认注销页面过滤器
     *  BasicAuthenticationFilter : 请求头认证过滤器
     */


    /**
     * 配置过滤器链  SecurityFilterChain,,spring security 所有功能都是通过过滤器链来提供
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 拦截所有，，经过某些过滤器
//        return new DefaultSecurityFilterChain(new AntPathRequestMatcher("/**"));


        // 默认的过滤器链
//        return http.build();

        http.authorizeHttpRequests(p->p.anyRequest().access(new AuthorizationManager<RequestAuthorizationContext>() {
                    /**
                     *
                     * @param authentication the {@link Supplier} of the {@link Authentication} to check  当前用户
                     * @param object the {@link T} object to check   当前请求
                     * @return
                     */
                    @Override
                    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
//                        authentication.
                        boolean granted = false;

                        // 匹配上了，才会做权限控制，，，数据库中没有匹配上，登录就能访问
                        boolean isMatch = false;


                        String requestURI = object.getRequest().getRequestURI();
                        Authentication auth = authentication.get();

                        // 查看这个url 访问需要的角色

                        List<MenuWithRole> menuWithRoles = menuService.getAllMenusWithRole();


                        for (MenuWithRole menuWithRole : menuWithRoles) {
                            if ( antPathMatcher().match(menuWithRole.getUrl(),requestURI)){
                                isMatch = true;
                                // 匹配上了，，比对角色
                                List<Role> roles = menuWithRole.getRoles();

                                // 具备的权限

                                // 最终调的是  UserDetails中的getAuthorities
                                Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

                                for (GrantedAuthority authority : authorities) {
                                    for (Role role : roles) {
                                        if (authority.getAuthority().equals(role.getName())){
                                            // 具备角色
                                            granted = true;
                                            break;
                                        }

                                    }
                                }
                            }
                        }


        // 数据库中没有拦截这个url，，，，没有匹配上，登录就能访问
                        if (!isMatch){
//                            if (auth instanceof UsernamePasswordAuthenticationToken){
//
//                            }
                            //  判断是否登录
                            if (auth.isAuthenticated()) {
                                granted = true;
                            }
                        }


                        /**
                         * granted为true ： 请求通过
                         * 为false： 不通过
                         */
                        return new AuthorizationDecision(granted);
                    }
                }))

//         http.authorizeHttpRequests(p->p.anyRequest().authenticated())
//                .formLogin(f->f.usernameParameter("username")
//                        .passwordParameter("password")
//                        .loginProcessingUrl("/login")
//                        .successHandler((req,resp,auth)->{
//                            resp.setContentType("application/json;charset=utf-8");
//                            Hr hr = (Hr) auth.getPrincipal();
//                            hr.setPassword(null);
//                            resp.getWriter().write(new ObjectMapper().writeValueAsString(RespBean.ok("登录成功",hr)));
//                        })
//                        .failureHandler((req,resp,e)->{
//                            resp.setContentType("application/json;charset=utf-8");
//                            RespBean error = RespBean.error("登录失败");
//
//                            if (e instanceof BadCredentialsException){
//                                error.setMessage("密码错误");
//                            }else if (e instanceof DisabledException){
//                                error.setMessage("用户被禁用");
//                            }else if (e instanceof LockedException){
//                                error.setMessage("账户被锁定");
//                            }else if (e instanceof AccountExpiredException){
//                                error.setMessage("账户过期");
//                            }else if(e instanceof CredentialsExpiredException){
//                                error.setMessage("密码过期");
//                            }
//
//                            resp.getWriter().write(new ObjectMapper().writeValueAsString(error));
//                        }))
                .csrf(c->c.disable())
                 //异常处理
                 .exceptionHandling(e->e.authenticationEntryPoint((req,resp,ex)->{
                     resp.setContentType("application/json;charset=utf-8");
                     resp.setStatus(401);
                     RespBean error = RespBean.error("尚未登陆，请登录");
                     resp.getWriter().write(new ObjectMapper().writeValueAsString(error));
                 }))
                         .logout(logout->{
                             logout.logoutUrl("/logout") // 注销链接
                                     .logoutSuccessHandler((req,resp,auth)->{
                                         resp.setContentType("application/json;charset=utf-8");
                                         Hr hr = (Hr) auth.getPrincipal();
                                         hr.setPassword(null);
                                         resp.getWriter().write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功",hr)));
                                     });// 注销成功
                         });

         // 加到 UsernamePasswordAuthenticationFilter前面
         http.addFilterBefore(jsonFilter(), UsernamePasswordAuthenticationFilter.class);

         return  http.build();

        /**
         * spring security 默认key-value
         * UsernamePasswordAuthenticationFilter
         */

    }

}
