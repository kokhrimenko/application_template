package com.kokhrimenko.spring.application.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * 
 * @author kostic
 *
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public static final String HOME_PAGE = "/home";
    public static final String LOGIN_PAGE = "/login";
    public static final String PROFILE_PAGE = "/profile";
    public static final String ROOT_PAGE = "/";
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(HOME_PAGE).setViewName("home");
        registry.addViewController(ROOT_PAGE).setViewName("home");
        registry.addViewController(PROFILE_PAGE).setViewName("profile");
        registry.addViewController(LOGIN_PAGE).setViewName("login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lng");
        return lci;
    }
}
