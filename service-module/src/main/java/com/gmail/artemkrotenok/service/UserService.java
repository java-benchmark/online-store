package com.gmail.artemkrotenok.service;

import com.gmail.artemkrotenok.service.model.UpdateUserDTO;
import com.gmail.artemkrotenok.service.model.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO add(UserDTO userDTO);

    UserDTO getUserByEmail(String email);

    Long getCountUsers();

    UserDTO getUserById(Long id);

    UserDTO changeUserRole(UpdateUserDTO updateUserDTO);

    List<UserDTO> getItemsByPageSorted(int page);

    void deleteUsersByIds(Long[] userIds);

    void changeUserPassword(Long id);

    UserDTO update(UserDTO userDTO);

}
