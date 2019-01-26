package com.nixsolutions.barchenko.webservice.soap;

import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.service.UserService;
import com.nixsolutions.barchenko.webservice.MyWebService;
import java.util.List;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;

@WebService(endpointInterface =
        "com.nixsolutions.barchenko.webservice.MyWebService",
        serviceName = "UserWebServiceSoapImpl")
public class UserWebServiceSoapImpl implements MyWebService {

    @Autowired
    private UserService userService;

    @Override
    public void create(User user) {
        if (user == null) {
            throw new NullPointerException("Couldn't create user");
        }
        userService.create(user);
    }

    @Override
    public void update(User user) {
        if (user == null) {
            throw new NullPointerException("Couldn't update user");
        }
        userService.update(user);
    }

    @Override
    public void remove(User user) {
        if (user == null) {
            throw new NullPointerException("Couldn't delete user");
        }
        userService.remove(user);
    }

    @Override
    public List<User> findAll() {
        return userService.findAll();
    }

    @Override
    public User findByLogin(String login) {
        if (login == null) {
            throw new NullPointerException("Couldn't find " + login);
        }
        return userService.findByLogin(login);
    }
}
