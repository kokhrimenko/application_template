package com.kokhrimenko.spring.application.security;

import static com.kokhrimenko.spring.application.mvc.MvcConfig.HOME_PAGE;
import static com.kokhrimenko.spring.application.mvc.MvcConfig.LOGIN_PAGE;
import static com.kokhrimenko.spring.application.mvc.MvcConfig.PROFILE_PAGE;
import static com.kokhrimenko.spring.application.mvc.MvcConfig.ROOT_PAGE;
import static com.kokhrimenko.spring.application.mvc.MvcConfig.REGISTER_PAGE;
import static com.kokhrimenko.spring.application.mvc.MvcConfig.USERS_PAGE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String USER_MANAGER_AUTHORITY = "MANAGE_USERS";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
            .authorizeRequests()
                .antMatchers(ROOT_PAGE, HOME_PAGE, REGISTER_PAGE).permitAll()
                .antMatchers(USERS_PAGE).hasAuthority(USER_MANAGER_AUTHORITY)
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage(LOGIN_PAGE)
                .defaultSuccessUrl(PROFILE_PAGE, true)
                .permitAll()
                .and()
            .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl(ROOT_PAGE)
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
