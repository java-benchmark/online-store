package com.gmail.artemkrotenok.service.util;

import com.gmail.artemkrotenok.repository.model.User;
import com.gmail.artemkrotenok.repository.model.UserInformation;
import com.gmail.artemkrotenok.service.model.UserDTO;

public class UserConverterUtil {

    public static UserDTO getDTOFromObject(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setSurname(user.getSurname());
        userDTO.setName(user.getName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        //userDTO.setAddress(user.getUserInformation().getAddress());
        //userDTO.setPhone(user.getUserInformation().getPhone());
        return userDTO;
    }

    public static User getObjectFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setSurname(userDTO.getSurname());
        user.setName(userDTO.getName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        UserInformation userInformation = new UserInformation();
        userInformation.setPhone(userDTO.getPhone());
        userInformation.setAddress(userDTO.getAddress());
        userInformation.setUser(user);
        user.setUserInformation(userInformation);
        return user;
    }

}
