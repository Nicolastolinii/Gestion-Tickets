package com.ticket.ticket.service;

import com.ticket.ticket.dto.TicketDTO;
import com.ticket.ticket.dto.TicketResponseDTO;
import com.ticket.ticket.model.Ticket;

import java.util.List;

public interface TicketService {
    Ticket ticketVerify(String codigo);

    List<TicketResponseDTO> getTicketByUser(Long userId);

    boolean existsById(Long eventId);

    void deleteTicket(Long ticketId);

    TicketResponseDTO createTicket(TicketDTO ticketDTO);
}
