package com.ticket.ticket.controller;



import com.ticket.ticket.dto.UserDTO;
import com.ticket.ticket.service.UserService;
import com.ticket.ticket.utils.CustomExceptions;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> userById(@PathVariable Long userId){
        try {
            UserDTO user = userService.getUserById(userId);
            if (user != null){
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.notFound().build();
        }catch (CustomExceptions.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 NOT FOUND
        }
    }

}
