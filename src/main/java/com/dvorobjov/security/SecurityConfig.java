package com.dvorobjov.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Dmytro Vorobiov on 21.06.2017.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private String USER_LOGIN = "user";
    private String USER_PASSWORD = "password";
    private String ADMINISTRATOR_LOGIN = "admin";
    private String ADMINISTRATOR_PASSWORD = "admin_password";


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .regexMatchers(HttpMethod.GET, "/account/.*").hasRole(Roles.USER.name())
                .regexMatchers(HttpMethod.GET, "/account/find/.*").hasRole(Roles.USER.name())
                .regexMatchers(HttpMethod.POST, "/account/.*").hasRole(Roles.ADMINISTRATOR.name())
                .regexMatchers(HttpMethod.PUT, "/account/.*").hasRole(Roles.ADMINISTRATOR.name())
                .regexMatchers(HttpMethod.DELETE, "/account/.*").hasRole(Roles.ADMINISTRATOR.name())
                .and()
                .httpBasic();
//        .headers()
//                .contentTypeOptions()
//                .and()
//                .xssProtection()
//                .and()
//                .cacheControl()
//                .and()
//                .httpStrictTransportSecurity()
//                .and()
//                .frameOptions()
//                .and()
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(USER_LOGIN).password(USER_PASSWORD).roles(Roles.USER.name())
                .and()
                .withUser(ADMINISTRATOR_LOGIN).password(ADMINISTRATOR_PASSWORD).roles(Roles.USER.name(), Roles.ADMINISTRATOR.name());
    }
}
