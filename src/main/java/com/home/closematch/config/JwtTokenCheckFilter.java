package com.home.closematch.config;

import com.home.closematch.entity.Account;
import com.home.closematch.pojo.Msg;
import com.home.closematch.service.AccountService;
import com.home.closematch.utils.AccountUtils;
import com.home.closematch.utils.JwtUtils;
import com.home.closematch.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
public class JwtTokenCheckFilter extends OncePerRequestFilter {

    private final List<String> whiteList =
            new ArrayList<>(Arrays.asList(
                    "/register", "/mail",
                    "/company", "/position",
                    "/swagger-resources", "/webjars", "/v2", "/swagger-ui"
            ));

    private boolean inspectInWhiteList(String uri) {
        for (String s : whiteList) {
            if (uri.startsWith(s))
                return true;
        }
        return false;
    }

    /**
     * 每次请求都会走这个方法
     * jwt 从header带过来
     * 解析jwt
     * 设置到上下文当中去
     * jwt 性能没有session好
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        // 拿到url
        String path = request.getRequestURI();
        String method = request.getMethod();
        if (inspectInWhiteList(path)){
            // 如果在白名单中, 则直接放行
            filterChain.doFilter(request, response);
            return;
        }

        // 判断是否为登录, 为登录则向下运行
        if ("/login".equals(path) && "POST".equalsIgnoreCase(method)) {
            // 直接放行 因为登录没有token
            filterChain.doFilter(request, response);
            return;
        }
        // 验证jwt
        String authorization = request.getHeader(JwtUtils.HEADER_AUTHORIZATION);
        if (StringUtils.hasText(authorization)) {
            // 如果有 那么就拿到真正的token
            String jwtString = authorization.replaceAll("bearer ", "");
            // 解析jwt
            try {
                JwtUtils.verifyJwtToken(jwtString);
            } catch (RuntimeException e) {
                // 报错了 写一个400出去
                VerifyUtils.sendFormatJson(response, Msg.fail(400, "Token 验证失败"));
//                response.getWriter().write("token验证失败");
                return;
            }
            // 拿到解析后的jwt了 后端服务器没保存用户信息 给他设置进去
            List<SimpleGrantedAuthority> userType = AccountUtils.getAuthorities(JwtUtils.getExpectField(jwtString, "role", String.class));

            Account account = new Account();
            account.setUsername(JwtUtils.getExpectField(jwtString, "username", String.class));
            account.setUserId(JwtUtils.getExpectField(jwtString, "userId", Long.class));
            account.setRole(JwtUtils.getExpectField(jwtString, "role", String.class));
            account.setUserType(JwtUtils.getExpectField(jwtString, "userType", Integer.class));
            account.setPassword("[PROTECTED]");

            // 变成security认识的对象
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, null, userType);
            // 怎么才能让security认识呢？
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 代码继续往下执行
            filterChain.doFilter(request, response);
            return;
        }
        // 返回错误信息
        VerifyUtils.sendFormatJson(response, Msg.fail(400, "Token 验证失败"));
    }
}
