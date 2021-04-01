package com.thanhhai.demo.filter;

import com.thanhhai.demo.auth.UserPrinciple;
import com.thanhhai.demo.constant.SecurityConstants;
import com.thanhhai.demo.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.SneakyThrows;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@PropertySource("classpath:values.properties")
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtils jwtUtils;
    private UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        ServletContext servletContext = req.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        jwtUtils = webApplicationContext.getBean(JwtUtils.class);
        userDetailsService = webApplicationContext.getBean(UserDetailsService.class);

        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = null;
        authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    @SneakyThrows
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        try {
            String token = request.getHeader(SecurityConstants.HEADER_STRING);

            if (token != null) {
                // parse the token
                token = token.substring(7);
                String username = jwtUtils.getUsernameFromToken(token);
                Claims claims = jwtUtils.getAllClaimsFromToken(token);
                List<String> roles = (List<String>) claims.get("role");
                Collection authorities = null;
                if (!CollectionUtils.isEmpty(roles)) {
                    authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                }
                if (username != null) {
                    UserPrinciple userPrinciple = (UserPrinciple) userDetailsService.loadUserByUsername(username);
                    // new arraylist means authorities
                    return new UsernamePasswordAuthenticationToken(userPrinciple, null, authorities);
                }
            }

        } catch (ExpiredJwtException ex) {
            request.setAttribute("jwt", ex.getMessage());
        } catch (MalformedJwtException ex) {
            request.setAttribute("jwt", ex.getMessage());
        }

        throw new AuthenticationException("Token invalid!");
    }

}
