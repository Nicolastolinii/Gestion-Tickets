package com.ticket.ticket.repository;

import com.ticket.ticket.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  EventoRepo extends JpaRepository<Evento, Long> {
}
