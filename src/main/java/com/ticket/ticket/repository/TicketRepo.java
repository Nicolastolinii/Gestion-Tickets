package com.ticket.ticket.repository;

import com.ticket.ticket.model.Ticket;
import com.ticket.ticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket, Long> {
    Ticket findByCodigo(String codigo);
    List<Ticket> findByUsuario_UserId(Long userId);
}
