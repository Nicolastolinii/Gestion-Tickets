package com.ticket.ticket.mapper;


import com.ticket.ticket.dto.UserDTO;
import com.ticket.ticket.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDTO(User user);

}
