package com.nixsolutions.barchenko.config.webserviceconfig;

import com.nixsolutions.barchenko.webservice.MyWebService;
import com.nixsolutions.barchenko.webservice.soap.UserWebServiceSoapImpl;
import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapConfig {

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), service());
        endpoint.publish("/users");
        return endpoint;
    }

    @Bean
    public MyWebService service() {
        return new UserWebServiceSoapImpl();
    }
}
