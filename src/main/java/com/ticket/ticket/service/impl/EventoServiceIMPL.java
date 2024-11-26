package com.ticket.ticket.service.impl;

import com.ticket.ticket.dto.EventoDTO;
import com.ticket.ticket.mapper.EventoMapper;
import com.ticket.ticket.model.Evento;
import com.ticket.ticket.repository.EventoRepo;
import com.ticket.ticket.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoServiceIMPL implements EventoService {
    @Autowired
    private EventoRepo eventoRepo;


    @Override
    public Evento saveEvento(Evento evento) {
        return eventoRepo.save(evento);
    }

    @Override
    public List<EventoDTO> getAllEvent(){
        List<Evento> evento = eventoRepo.findAll();
        List<EventoDTO> eventoDTOs = evento.stream()
                .map(EventoMapper.INSTANCE::toEventoDTO)
                .collect(Collectors.toList());
        return eventoDTOs;
    }
    @Override
    public Evento getEventoById(Long id) {
        return eventoRepo.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long eventId) {
        return eventoRepo.existsById(eventId);
    }


    @Override
    public Boolean updateEvent(Long eventId, Evento event){
        Evento existingEvent = eventoRepo.findById(eventId).orElse(null);
        if (existingEvent != null){
            existingEvent.setCapacidad(event.getCapacidad());
            existingEvent.setHora(event.getHora());
            existingEvent.setFecha(event.getFecha());
            existingEvent.setDescripcion(event.getDescripcion());
            existingEvent.setNombre(event.getNombre());
            existingEvent.setPrecio(event.getPrecio());
            existingEvent.setUbicacion(event.getUbicacion());
            eventoRepo.save(existingEvent);
            return true;
        }else {
            return false;
        }

    }



    @Override
    public void deleteEvent(Long eventId){
        eventoRepo.deleteById(eventId);
    }
}
