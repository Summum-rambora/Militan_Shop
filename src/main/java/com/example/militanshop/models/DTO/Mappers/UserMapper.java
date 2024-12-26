package com.example.militanshop.models.DTO.Mappers;

import com.example.militanshop.models.DTO.RegistrationDTO;
import com.example.militanshop.models.DTO.UserDTO;
import com.example.militanshop.models.Role;
import com.example.militanshop.models.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setActive(user.isActive());
        userDTO.setRoles(user.getRoles().stream().
                map(Enum::name)
                .collect(Collectors.toSet()));

        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setActive(userDTO.isActive());
        user.setRoles(userDTO.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet()));

        return user;
    }

    public static User toEntity(RegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(registrationDTO.getPassword());

        return user;


    }
}