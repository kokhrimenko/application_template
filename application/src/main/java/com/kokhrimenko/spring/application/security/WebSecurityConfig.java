package com.kokhrimenko.spring.application.security;

import static com.kokhrimenko.spring.application.mvc.MvcConfig.HOME_PAGE;
import static com.kokhrimenko.spring.application.mvc.MvcConfig.LOGIN_PAGE;
import static com.kokhrimenko.spring.application.mvc.MvcConfig.ROOT_PAGE;
import static com.kokhrimenko.spring.application.mvc.MvcConfig.PROFILE_PAGE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
            .authorizeRequests()
                .antMatchers(ROOT_PAGE, HOME_PAGE).permitAll()
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
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                    .username("user@gmail.com")
                    .password("password")
                    .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
