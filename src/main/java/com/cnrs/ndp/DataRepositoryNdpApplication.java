package com.cnrs.ndp;

import com.sun.faces.config.ConfigureListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.util.Arrays;

@SpringBootApplication
public class DataRepositoryNdpApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataRepositoryNdpApplication.class, args);
    }


    @Bean
    ServletRegistrationBean jsfServletRegistration (ServletContext servletContext) {
        //spring boot only works if this is set
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
        servletContext.setInitParameter("facelets.DEVELOPMENT", Boolean.TRUE.toString());

        servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
        servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
        servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
        servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/springsecurity.taglib.xml");
        servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());

        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());

        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
        servletContext.setInitParameter("primefaces.THEME", "ui-lightness");
        servletContext.setInitParameter("primefaces.UPLOADER", "commons");
        servletContext.setInitParameter("primefaces.MOVE_SCRIPTS_TO_BOTTOM", Boolean.TRUE.toString());

        //registration
        ServletRegistrationBean srb = new ServletRegistrationBean();
        srb.setServlet(new FacesServlet());
        srb.setUrlMappings(Arrays.asList("*.xhtml"));
        srb.setLoadOnStartup(1);

        return srb;
    }

    @Bean
    public FacesServlet facesServlet() {
      return new FacesServlet();
    }

    @Bean
    public ServletRegistrationBean<FacesServlet> facesServletServletRegistrationBean(@Autowired FacesServlet facesServlet) {
        ServletRegistrationBean<FacesServlet> servletRegistrationBean = new ServletRegistrationBean(facesServlet, "*.xhtml");
        servletRegistrationBean.setLoadOnStartup(1);
        servletRegistrationBean.setName("ServeletNames");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean primeFacesFileUploadFilter(@Autowired ServletRegistrationBean<FacesServlet> facesServletServletRegistrationBean) {
        FilterRegistrationBean registration = new FilterRegistrationBean(new org.primefaces.webapp.filter.FileUploadFilter(), facesServletServletRegistrationBean);
        registration.setName("primeFacesFileUploadFilter");
        registration.addUrlPatterns("/*");
        registration.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST);
        return registration;
    }

    @Bean
    public ServletRegistrationBean facesServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
        registration.setLoadOnStartup(1);
        registration.addUrlMappings("*.jr");
        return registration;
    }

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
          servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
          servletContext.setInitParameter("primefaces.THEME", "sunny");
        };
    }

    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<>(new ConfigureListener());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
