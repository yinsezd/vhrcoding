package org.javaboy.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaboy.vhr.model.RespBean;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 验证码过滤器
 */
@Component
public class VerifyCodeConfig extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //登录请求
        if("POST".equals(request.getMethod())&&"/doLogin".equals(request.getServletPath())){
            String code = request.getParameter("code");
            String  vercode = (String)request.getSession().getAttribute("ver_code");
            if(code==null||"".equals(code)||!vercode.toLowerCase().equals(code.toLowerCase())){
               //验证码不正确
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.write(new ObjectMapper().writeValueAsString(RespBean.error("验证码填写错误！")));
                out.flush();
                out.close();
                return;
            }else {
                filterChain.doFilter(request,response);
            }
        }else {
            filterChain.doFilter(request,response);
        }

    }
}
