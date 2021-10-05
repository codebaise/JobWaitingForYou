package com.home.closematch.config;

import com.home.closematch.entity.Account;
import com.home.closematch.pojo.Msg;
import com.home.closematch.service.impl.AccountServiceImpl;
import com.home.closematch.utils.JwtUtils;
import com.home.closematch.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private JwtTokenCheckFilter jwtTokenCheckFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountServiceImpl).passwordEncoder(password());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 设置filter 在 UsernamePasswordAuthenticationFilter 之前
        http.addFilterBefore(jwtTokenCheckFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
        http.cors();
        http.formLogin()
//                .loginProcessingUrl("/login")
                .permitAll();

        // 禁用掉session 因为我们用的前后端分离的模式 根本不需要session 只要你有token (jwt) 就可以访问
        http.sessionManagement().disable();
        // 放行所有的接口 因为我们使用了jwt 只要请求携带了jwt  只要能被我解析 那么就的相当于登录成功了
        http.authorizeRequests()
                .antMatchers("/**")
                .permitAll();
    }

    @Bean
    public PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            // 登录成功后到这里, 手动填入jwt_token
            response.setContentType("application/json;charset=utf-8");
            // PrintWriter out = response.getWriter();
            Account account = (Account) authentication.getPrincipal();
            HashMap<String, Object> claimsMap = new HashMap<>();
            claimsMap.put("username", account.getUsername());
            claimsMap.put("userId", account.getUserId());
            claimsMap.put("role", account.getRole());
            claimsMap.put("userType", account.getUserType());
            String token = JwtUtils.createJWT(claimsMap);
            VerifyUtils.sendFormatJson(response, Msg.success().add("token", token));
        });
        // 登录失败的时候走这里
        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            // 写出去
            VerifyUtils.sendFormatJson(response, Msg.fail(401, "用户名/密码错误"));
        });
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/login");
        return loginFilter;
    }
}
