package com.ticket.ticket.service.impl;


import com.ticket.ticket.model.User;
import com.ticket.ticket.repository.UserRepo;
import com.ticket.ticket.service.AuthService;
import com.ticket.ticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class AuthServiceIMPL implements AuthService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;



    @Override
    public boolean validateJwtToken(String token) {
        return false;
    }


}
