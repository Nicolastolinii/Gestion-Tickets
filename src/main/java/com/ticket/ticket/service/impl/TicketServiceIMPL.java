package com.ticket.ticket.service.impl;

import com.ticket.ticket.dto.TicketDTO;
import com.ticket.ticket.dto.TicketResponseDTO;
import com.ticket.ticket.mapper.TicketMapper;
import com.ticket.ticket.model.Evento;
import com.ticket.ticket.model.Ticket;
import com.ticket.ticket.model.User;
import com.ticket.ticket.repository.EventoRepo;
import com.ticket.ticket.repository.TicketRepo;
import com.ticket.ticket.repository.UserRepo;
import com.ticket.ticket.service.TicketService;
import com.ticket.ticket.utils.CustomExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceIMPL implements TicketService {
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EventoRepo eventoRepo;
    @Autowired
    private TicketMapper ticketMapper;


    @Override
    public Ticket ticketVerify(String codigo) {
        return ticketRepo.findByCodigo(codigo);
    }

    @Override
    public List<TicketResponseDTO> getTicketByUser(Long userId){
        List<Ticket> tickets = ticketRepo.findByUsuario_UserId(userId);

        List<TicketResponseDTO> ticketDTOs = tickets.stream()
                .map(ticket -> ticketMapper.ticketToTicketResponseDTO(ticket))
                .collect(Collectors.toList());

        return ticketDTOs;
    }
    @Override
    public boolean existsById(Long eventId) {
        return ticketRepo.existsById(eventId);
    }

    @Override
    public void  deleteTicket(Long ticketId){
        ticketRepo.deleteById(ticketId);
    }

    @Override
    public TicketResponseDTO createTicket(TicketDTO ticketDTO) {
        User user = userRepo.findById(ticketDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Evento evento = eventoRepo.findById(ticketDTO.getEventoId())
                .orElseThrow(() -> new CustomExceptions.EventoNoEncontradoException("Evento no encontrado"));
        if (evento.getTicketsVendidos() + ticketDTO.getCantidad() > evento.getCapacidad()){
            throw new CustomExceptions.CapacidadInsuficienteException("Cantidad de tickets solicitada no disponible.");
        }
        if (evento != null && !evento.isSoldOut() && evento.getCapacidad() >= evento.getTicketsVendidos() && evento.getCapacidad() >= ticketDTO.getCantidad()  ) {

            Ticket ticket = new Ticket();
            ticket.setFechaCompra(LocalDateTime.now().toString());
            ticket.setCantidad(ticketDTO.getCantidad());
            ticket.setEvento(evento);
            ticket.setUsuario(user);

            evento.setTicketsVendidos(evento.getTicketsVendidos() + ticketDTO.getCantidad());
            if (evento.getTicketsVendidos() >= evento.getCapacidad()) {
                evento.setSoldOut(true);
            }
            System.out.println(ticket);
            System.out.println(user);

            ticketRepo.save(ticket);
            System.out.println(user.getTickets());
            userRepo.save(user);
            eventoRepo.save(evento);
            return ticketMapper.ticketToTicketResponseDTOWithUserId(ticket);
        } else {
            throw new CustomExceptions.EventoSoldOutException("Cantidad de tickets solicitada no disponible.");
        }
    }
}
