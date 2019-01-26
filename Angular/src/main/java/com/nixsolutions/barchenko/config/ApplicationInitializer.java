package com.nixsolutions.barchenko.config;

import com.nixsolutions.barchenko.config.webserviceconfig.SoapConfig;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext container) {

        AnnotationConfigWebApplicationContext context =
                new AnnotationConfigWebApplicationContext();
        context.register(CORSFilter.class);
        context.register(WebConfig.class);
        context.register(WebSecurityConfig.class);
        context.setServletContext(container);

        container.addListener(new ContextLoaderListener(context));

        container.setInitParameter("contextConfigLocation",
                "com.nixsolutions.barchenko");

        ServletRegistration.Dynamic servlet = container.addServlet(
                "dispatcher", new DispatcherServlet(context));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");

        ServletRegistration.Dynamic rest = container.addServlet(
                "jerseyS", new SpringServlet());
        rest.addMapping("/rest/*", "/temp");

        CXFServlet cxfServlet = new CXFServlet();
        BusFactory.setDefaultBus(cxfServlet.getBus());
        ServletRegistration.Dynamic dynamic = container
                .addServlet("cxf", cxfServlet);

        dynamic.setLoadOnStartup(2);
        dynamic.addMapping("/soap/*");

    }
}
