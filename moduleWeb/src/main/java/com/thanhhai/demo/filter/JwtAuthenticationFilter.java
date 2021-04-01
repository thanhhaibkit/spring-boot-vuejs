package com.thanhhai.demo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanhhai.demo.auth.UserPrinciple;
import com.thanhhai.demo.constant.SecurityConstants;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@PropertySource(value = {"classpath:application.properties"})
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtUtils jwtUtils;
    private DomainUtils domainUtils;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext ctx) {
        setAuthenticationManager(authenticationManager);

        setFilterProcessesUrl(SecurityConstants.LOG_IN_URL);
        this.jwtUtils= ctx.getBean(JwtUtils.class);
        this.domainUtils= ctx.getBean(DomainUtils.class);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res
    ) throws AuthenticationException {
        try {
            UserPrinciple credentials = new ObjectMapper()
                    .readValue(req.getInputStream(), UserPrinciple.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
        protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
            FilterChain chain, Authentication auth) {
        try {

            String jsonString = jwtUtils.doGenerateToken(auth);
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            PrintWriter out = res.getWriter();
            out.print(jsonString);
            out.flush();
        } catch (Exception e) {
            req.setAttribute("jwt", e.getMessage());
        }

    }
}
