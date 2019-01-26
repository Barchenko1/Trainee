package com.nixsolutions.barchenko.service.impl;

import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {
        User user = userService.findByLogin(name);
        UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User
                    .withUsername(name);
            builder.password(user.getPassword());
            String authorities;
            if (user.getRole().getName().equals("ADMIN")) {
                authorities = "ADMIN";
            } else {
                authorities = "USER";
            }
            builder.authorities(authorities);
        }
        return builder.build();

    }

}
