package org.javaboy.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaboy.vhr.model.Hr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JKXAING on 2020/9/9
 *
 * 自定义过滤器
 *
 * 想实现用户登录控制：
 * 最大的问题在于我们用自定义的过滤器代替了 UsernamePasswordAuthenticationFilter，
 * 进而导致前面所讲的关于 session 的配置，统统失效。所有相关的配置我们都要在新的过滤器 LoginFilter
 * 中进行配置 ，包括 SessionAuthenticationStrategy 也需要我们自己手动配置了。
 *
 * 这里我们要自己提供 SessionAuthenticationStrategy，
 * 而前面处理 session 并发的是 ConcurrentSessionControlAuthenticationStrategy，也就是说，
 * 我们需要自己提供一个 ConcurrentSessionControlAuthenticationStrategy 的实例，
 * 然后配置给 LoginFilter，但是在创建 ConcurrentSessionControlAuthenticationStrategy 实例的过程中，
 * 还需要有一个 SessionRegistryImpl 对象
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        //验证码检查
        String verify_code = (String) request.getSession().getAttribute("verify_code");

        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)
        || request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            Map<String, String> loginData = new HashMap<>();
            try {
                loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
            }finally {
                String code = loginData.get("code");
                checkCode(response, code, verify_code);
            }
            String username = loginData.get(getUsernameParameter());
            String password = loginData.get(getPasswordParameter());
            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }
            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, password);
            setDetails(request, authRequest);

            //处理完登录数据之后，手动向 SessionRegistryImpl 中添加一条记录：
            Hr principal = new Hr();
            principal.setUsername(username);
            sessionRegistry.registerNewSession(request.getSession(true).getId(), principal);
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            checkCode(response, request.getParameter("code"), verify_code);
            return super.attemptAuthentication(request, response);
        }
    }

    public void checkCode(HttpServletResponse resp, String code, String verify_code) {
        if (code == null || verify_code == null || "".equals(code) || !verify_code.toLowerCase().equals(code.toLowerCase())) {
            //验证码不正确
            throw new AuthenticationServiceException("验证码不正确");
        }
    }
}