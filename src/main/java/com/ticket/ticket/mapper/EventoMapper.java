package com.ticket.ticket.mapper;
import com.ticket.ticket.dto.EventoDTO;
import com.ticket.ticket.model.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface EventoMapper {
    EventoMapper INSTANCE = Mappers.getMapper(EventoMapper.class);

    EventoDTO toEventoDTO(Evento evento);
}
