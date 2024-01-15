package kz.group36.springboot.mapper;

import kz.group36.springboot.dto.UserDTO;
import kz.group36.springboot.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper (componentModel = "spring")
public interface UserMapper {
    UserDTO toDto (User user);
    User toEntity (UserDTO userDTO);

    List<UserDTO> toDtoList (List<User> tasks);
}