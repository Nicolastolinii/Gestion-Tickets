package com.ticket.ticket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String fecha;

    @Column(nullable = false)
    private String hora;

    @Column(nullable = false)
    private String ubicacion;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private int capacidad; // Capacidad total de tickets para el evento

    @Column(nullable = false)
    private int ticketsVendidos;

    private boolean soldOut = false;

    // un evento puede tener múltiples tickets vendidos
    @OneToMany(mappedBy = "evento")
    private Set<Ticket> tickets;

}
