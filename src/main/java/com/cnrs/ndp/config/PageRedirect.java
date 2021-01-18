package com.cnrs.ndp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class PageRedirect implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.xhtml");
        registry.addViewController("/depot").setViewName("forward:/depot.xhtml");
        registry.addViewController("/connexion").setViewName("forward:/connexion.xhtml");
        registry.addViewController("/gestion_depot").setViewName("forward:/gestion_depot.xhtml");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}