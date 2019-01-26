package com.nixsolutions.barchenko.webservice.rest;

import com.nixsolutions.barchenko.entity.LoginPassword;
import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.service.UserService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/temp")
public class UserLoginServiceRest {
    private final String messageForAdmin =
            new String("{\"message\":\"admin\"}");
    private final String messageForUser =
            new String("{\"message\":\"user\"}");
    private final String messageWrong =
            new String("{\"message\":\"invalid Credentials\"}");

    @Autowired
    private UserService userService;

    @POST
    @Produces({ MediaType.APPLICATION_JSON})
    public Response postUser(LoginPassword client) {
        String login = client.getUsername();
        User user = userService.findByLogin(login);
        if (user != null) {
            if (user.getPassword().equals(client.getPassword())) {
                if (user.getRole().getRoleId() == 1L) {
                    return Response
                            .status(Response.Status.OK)
                            .entity(messageForAdmin)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
                } else {
                    return Response
                            .status(Response.Status.OK)
                            .entity(messageForUser)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
                }
            }
        }
        return Response
                .status(Response.Status.OK)
                .entity(messageWrong)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
