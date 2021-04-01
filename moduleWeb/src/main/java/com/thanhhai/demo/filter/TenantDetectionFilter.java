package com.thanhhai.demo.filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import phoneappli.booking.config.TenantContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TenantDetectionFilter extends OncePerRequestFilter {

    private DomainUtils domainUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {
        String url = req.getRequestURL().toString();
        URI uri;
        try {
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
            ServletContext servletContext = req.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            domainUtils = webApplicationContext.getBean(DomainUtils.class);

            uri = new URI(url);
            String host = uri.getHost();
            String subDomain = domainUtils.getSubDomainFromHost(host);
            if (subDomain != null) {
                TenantContext.setCurrentTenant(subDomain);
            }

            chain.doFilter(req, res);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
