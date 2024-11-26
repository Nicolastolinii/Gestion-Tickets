package com.ticket.ticket.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Column(nullable = false)
    private String fechaCompra;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false, unique = true)
    private String codigo = UUID.randomUUID().toString(); // Código único para el ticket

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

}
