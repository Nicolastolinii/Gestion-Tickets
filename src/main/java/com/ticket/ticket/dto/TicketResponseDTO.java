package com.ticket.ticket.dto;

import lombok.Data;

@Data
public class TicketResponseDTO {
    private Long ticketId;
    private Long userId;
    private String fechaCompra;
    private int cantidad;
    private String codigo;  // El código único del ticket
    private EventoDTO evento;
}
