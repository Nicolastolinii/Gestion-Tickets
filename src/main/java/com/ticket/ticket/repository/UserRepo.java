package com.ticket.ticket.repository;

import com.ticket.ticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findUserByUserId(Long userId);
    Optional<User> findByUsername(String username);
    @Query("SELECT u.email FROM User u")
    List<String> findAllEmails();
}
