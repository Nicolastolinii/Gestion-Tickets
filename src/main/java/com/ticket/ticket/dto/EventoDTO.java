package com.ticket.ticket.dto;

import lombok.Data;

@Data
public class EventoDTO {
    private String nombre;
    private String fecha;
    private String hora;
    private String ubicacion;
    private String descripcion;
    private Double precio;
    private int capacidad; // Capacidad total de tickets para el evento
    private boolean soldOut;
}
