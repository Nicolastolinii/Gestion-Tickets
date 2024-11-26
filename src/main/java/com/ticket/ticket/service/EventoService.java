package com.ticket.ticket.service;

import com.ticket.ticket.dto.EventoDTO;
import com.ticket.ticket.model.Evento;

import java.util.List;

public interface EventoService {
    Evento saveEvento(Evento evento);

    List<EventoDTO> getAllEvent();

    Evento getEventoById(Long id);

    boolean existsById(Long eventId);

    Boolean updateEvent(Long eventId, Evento event);

    void deleteEvent(Long eventId);
}
