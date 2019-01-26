package com.nixsolutions.barchenko.config;

import com.nixsolutions.barchenko.config.webserviceconfig.SoapConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {

        AnnotationConfigWebApplicationContext context =
                new AnnotationConfigWebApplicationContext();
        context.register(SoapConfig.class);
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

        //soap
        CXFServlet cxfServlet = new CXFServlet();
        BusFactory.setDefaultBus(cxfServlet.getBus());
        ServletRegistration.Dynamic soap = container.addServlet(
                "cxf", cxfServlet);

        soap.setLoadOnStartup(2);
        soap.addMapping("/soap/*");
    }

}
