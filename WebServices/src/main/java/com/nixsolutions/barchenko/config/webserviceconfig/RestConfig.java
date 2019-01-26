package com.nixsolutions.barchenko.config.webserviceconfig;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.web.filter.RequestContextFilter;

@ApplicationPath("/rest/*")
public class RestConfig extends ResourceConfig {

    public RestConfig() {
        packages("com.nixsolutions.barchenko.webservice.rest");
        register(RequestContextFilter.class);
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
    }
}
