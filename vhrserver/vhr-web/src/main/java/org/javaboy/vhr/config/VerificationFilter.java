package org.javaboy.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaboy.vhr.model.RespBean;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author JKXAING on 2020/6/18
 */
//@Component
public class VerificationFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if(req.getMethod().equals("POST") && req.getServletPath().equals("/doLogin")){

            String code = req.getParameter("code");
            String verfiyCode = (String)req.getSession().getAttribute("verify_code");
            if (code == null || code.equals("") || !code.equalsIgnoreCase(verfiyCode)) {
                //验证码不正确
                resp.setContentType("application/json;charset=utf-8");

                PrintWriter writer = resp.getWriter();
                RespBean respBean = RespBean.error("验证码错误");
                writer.write(new ObjectMapper().writeValueAsString(respBean) );
                writer.flush();
                writer.close();
                return;
            }
        }

        filterChain.doFilter(req, resp);

    }
}
