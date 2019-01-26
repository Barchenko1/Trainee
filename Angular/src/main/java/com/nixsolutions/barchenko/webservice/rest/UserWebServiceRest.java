package com.nixsolutions.barchenko.webservice.rest;

import com.nixsolutions.barchenko.entity.LoginPassword;
import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.service.RoleService;
import com.nixsolutions.barchenko.service.UserService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Path("/users")
@Service
@Component
public class UserWebServiceRest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GET
    @Path("/{login}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUser(@PathParam("login") String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST
                    .getStatusCode()).build();
        }
        return Response.status(Response.Status.OK.getStatusCode()).entity(user)
                .build();
    }

    @GET
    @Path("/userId/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserById(@PathParam("id") Long id) {
        User user = userService.findByUserId(id);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST
                    .getStatusCode()).build();
        }
        return Response.status(Response.Status.OK.getStatusCode()).entity(user)
                .build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getUsers() {
        return userService.findAll();
    }

    @POST
    @Path("/create")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createUser(User user) {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
                    .build();
        }
        userService.create(user);
        return Response.status(Response.Status.CREATED.getStatusCode())
                .entity(user.getLogin()).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/edit")
    public Response updateUser(User user) {
        User userToEdit = userService.findByLogin(user.getLogin());
        if (userToEdit == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
                    .build();
        }
        userToEdit.setPassword(user.getPassword());
        userToEdit.setFirstName(user.getFirstName());
        userToEdit.setLastName(user.getLastName());
        userToEdit.setEmail(user.getEmail());
        userToEdit.setBirthday(user.getBirthday());
        userToEdit.setRole(user.getRole());
        userService.update(userToEdit);
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(user.getLogin()).build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("delete/{login}")
    public Response removeUser(@PathParam("login") String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
                    .build();
        }
        userService.remove(user);
        return Response.status(Response.Status.OK.getStatusCode())
                .entity("User deleted").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/roles")
    public List<Role> getRoles() {
        return roleService.findAll();

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/roleId/{id}")
    public Response findRoleById(@PathParam("id") Long id) {
        Role role = roleService.findById(id);
        if (role == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
                    .build();
        }
        return Response.status(Response.Status.OK.getStatusCode()).entity(role)
                .build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/roleName/{name}")
    public Response findRoleByLogin(@PathParam("name") String name) {
        Role role = roleService.findByName(name);
        if (role == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
                    .build();
        }
        return Response.status(Response.Status.OK.getStatusCode()).entity(role)
                .build();
    }


}
