package com.ticket.ticket.mapper;

import com.ticket.ticket.dto.TicketResponseDTO;
import com.ticket.ticket.model.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketResponseDTO ticketToTicketResponseDTO(Ticket ticket);

    default TicketResponseDTO ticketToTicketResponseDTOWithUserId(Ticket ticket) {
        TicketResponseDTO ticketResponseDTO = ticketToTicketResponseDTO(ticket);
        ticketResponseDTO.setUserId(ticket.getUsuario().getUserId());  // Asignar el userId
        return ticketResponseDTO;
    }
}
