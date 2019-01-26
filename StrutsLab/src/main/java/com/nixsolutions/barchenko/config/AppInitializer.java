package com.nixsolutions.barchenko.config;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext context =
                new AnnotationConfigWebApplicationContext();
        ContextLoaderListener contextLoaderListener =
                new ContextLoaderListener(context);
        container.addListener(contextLoaderListener);
        context.register(WebConfig.class);
        context.register(WebSecurityConfig.class);
        context.setServletContext(container);
        ServletRegistration.Dynamic servlet = container
                .addServlet("dispatcher", new DispatcherServlet(context));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");

        FilterRegistration.Dynamic strutsDispatcher = container.addFilter(
                "StrutsDispatcher", new StrutsPrepareAndExecuteFilter());
        strutsDispatcher.addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST),
                true, "/*");
    }
}
