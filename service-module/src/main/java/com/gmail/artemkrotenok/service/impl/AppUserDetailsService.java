package com.gmail.artemkrotenok.service.impl;

import com.gmail.artemkrotenok.service.UserService;
import com.gmail.artemkrotenok.service.model.AppUserPrincipal;
import com.gmail.artemkrotenok.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final UserService userService;

    public AppUserDetailsService(
            UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDTO user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User is not found for e-mail: '" + email + "'");
        }
        logger.info("User for '" + email + "' was found");
        return new AppUserPrincipal(user);
    }

}
