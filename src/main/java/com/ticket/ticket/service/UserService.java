package com.ticket.ticket.service;

import com.ticket.ticket.auth.AuthResponse;
import com.ticket.ticket.dto.UserDTO;
import com.ticket.ticket.model.User;

public interface UserService {
    AuthResponse login(User user);

    AuthResponse registerUser(User user);

    UserDTO getUserById(Long userId);

    User getUserByEmail(String email);

    void deleteUser(Long userId);

}
