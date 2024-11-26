package com.ticket.ticket.dto;

import com.ticket.ticket.model.Rol;

import lombok.Data;

import java.util.List;


@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Rol rol;
    private List<TicketResponseDTO> tickets;
}
