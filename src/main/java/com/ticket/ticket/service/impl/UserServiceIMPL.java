package com.ticket.ticket.service.impl;

import com.ticket.ticket.auth.AuthResponse;
import com.ticket.ticket.dto.TicketDTO;
import com.ticket.ticket.dto.TicketResponseDTO;
import com.ticket.ticket.dto.UserDTO;
import com.ticket.ticket.mapper.TicketMapper;
import com.ticket.ticket.model.Rol;
import com.ticket.ticket.model.User;
import com.ticket.ticket.repository.TicketRepo;
import com.ticket.ticket.repository.UserRepo;
import com.ticket.ticket.service.JwtService;
import com.ticket.ticket.service.TicketService;
import com.ticket.ticket.service.UserService;
import com.ticket.ticket.utils.CustomExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private TicketService ticketService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthResponse login(User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        UserDetails username=userRepo.findByUsername(user.getUsername()).orElseThrow();
        String token=jwtService.getToken(username);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponse registerUser(User user){
        if (user == null) {
            throw new IllegalArgumentException("El objeto usuario no puede ser nulo.");
        }
        User username = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode( user.getPassword()))
                .email(user.getEmail())
                .rol(Rol.CLIENTE)
                .build();


        userRepo.save(username);
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User username = userRepo.findUserByUserId(userId);
        if (username != null){
            List<TicketResponseDTO> tickets = ticketService.getTicketByUser(userId);
            UserDTO userDTO = new UserDTO();
            userDTO.setId(username.getUserId());
            userDTO.setEmail(username.getEmail());
            userDTO.setUsername(username.getUsername());
            userDTO.setRol(username.getRol());
            userDTO.setTickets(tickets);
            return userDTO;
        }
        throw new CustomExceptions.UserNotFoundException("Usuario no encontrado con ID: " + userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.deleteById(userId);
    }

}
