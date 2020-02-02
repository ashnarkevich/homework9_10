package com.gmail.petrikov05.app.service;

import java.util.List;

import com.gmail.petrikov05.app.service.model.AddUserDTO;
import com.gmail.petrikov05.app.service.model.UserDTO;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO add(AddUserDTO addUserDTO);

    Integer delete(Long id);

}
