package com.nixsolutions.barchenko.webservice;

import com.nixsolutions.barchenko.entity.User;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.springframework.stereotype.Component;

@WebService
@Component
public interface MyWebService {

    @WebMethod
    void create(User user);

    @WebMethod
    void update(User user);

    @WebMethod
    void remove(User user);

    @WebMethod
    List<User> findAll();

    @WebMethod
    User findByLogin(String login);
}
