package com.ticket.ticket.service;

import com.ticket.ticket.model.User;

public interface AuthService {
    boolean validateJwtToken(String token);

}
