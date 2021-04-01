package com.thanhhai.demo.config;

import com.thanhhai.demo.constant.SecurityConstants;
import com.thanhhai.demo.filter.JwtAuthenticationEntryPoint;
import com.thanhhai.demo.filter.JwtAuthenticationFilter;
import com.thanhhai.demo.filter.JwtAuthorizationFilter;
import com.thanhhai.demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final List<String> URL_ACCESS_METHOD_GET = List.of(
            "/",
            "/css/*",
            "/js/*",
            "/img/*",
            "/fonts/*",
            "/favicon.ico",
            "/api/v1/reservation/**",
            SecurityConstants.SIGN_UP_URL,
            SecurityConstants.LOG_IN_URL,
            "/",
            "/my-page",
            "/booking-form",
            "/register",
            "/change-booking",
            "/booking-manage",
            "/booking-manage-detail",
            "/new-inquiry",
            "/inquiry-detail/*",
            "/login",
            "/reservation",
            "/registration",
            "/covid-booking",
            "/dev",
            "/faq",
            "/account-management",
            "/new-account",
            "/dashboard",
            "/booking-success",
            "/update-account/*",
            "/linehook",
            "/password/new",
            "/password/edit",
            "/reset-password",
            "/custom-login",
            "/api/v1/auth/custom-login",
            "/api/v1/booking-from-line/**"
    );

    private static final List<String> URL_ACCESS_METHOD_POST = List.of(
            "/api/v1/reservation/**",
            "/dashboard/*"
    );

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    //@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable().authorizeRequests()
                .antMatchers(URL_ACCESS_METHOD_GET.toArray(new String[URL_ACCESS_METHOD_GET.size()]))
                .permitAll()
                .antMatchers(  "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/favicon.*",
                        "/webjars/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, URL_ACCESS_METHOD_POST.toArray(new String[URL_ACCESS_METHOD_POST.size()]))
                .permitAll()
                .anyRequest().authenticated()
                .and()
                // Authentication by username and password
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), getApplicationContext()))
                // Authorization by token
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()

                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    public FilterRegistrationBean simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // *** URL below needs to match the Vue client URL and port ***
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}