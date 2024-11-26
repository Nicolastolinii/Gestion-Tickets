package com.ticket.ticket.controller;



import com.ticket.ticket.model.User;
import com.ticket.ticket.repository.UserRepo;
import com.ticket.ticket.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;


    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        return ResponseEntity.ok(userService.login(user));
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        if (user.getPassword() == null) {
            throw new IllegalArgumentException("La contraseña no puede ser nula");
        }
        List<String> emails = userRepo.findAllEmails();
        if (emails.contains(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: El correo ya está registrado.");
        }
        return ResponseEntity.ok(userService.registerUser(user));
    }

}
