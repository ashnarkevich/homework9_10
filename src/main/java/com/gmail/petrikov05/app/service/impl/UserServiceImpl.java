package com.gmail.petrikov05.app.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gmail.petrikov05.app.repository.ConnectionRepository;
import com.gmail.petrikov05.app.repository.UserInformationRepository;
import com.gmail.petrikov05.app.repository.UserRepository;
import com.gmail.petrikov05.app.repository.impl.ConnectionRepositoryImpl;
import com.gmail.petrikov05.app.repository.impl.UserInformationRepositoryImpl;
import com.gmail.petrikov05.app.repository.impl.UserRepositoryImpl;
import com.gmail.petrikov05.app.repository.model.User;
import com.gmail.petrikov05.app.repository.model.UserInformation;
import com.gmail.petrikov05.app.service.UserService;
import com.gmail.petrikov05.app.service.model.AddUserDTO;
import com.gmail.petrikov05.app.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static UserService instance;
    private ConnectionRepository connectionRepository;
    private UserRepository userRepository;
    private UserInformationRepository userInformationRepository;

    private UserServiceImpl(
            ConnectionRepository connectionRepository,
            UserRepository userRepository,
            UserInformationRepository userInformationRepository) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
        this.userInformationRepository = userInformationRepository;
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl(
                    ConnectionRepositoryImpl.getInstance(),
                    UserRepositoryImpl.getInstance(),
                    UserInformationRepositoryImpl.getInstance());
        }
        return instance;
    }

    @Override
    public List<UserDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> userList = userRepository.findAll(connection);
                List<UserDTO> userDTOList = convertUserListToUserDTOList(userList);
                connection.commit();
                return userDTOList;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
                throw new SQLException("Find user failed");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public UserDTO add(AddUserDTO addUserDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = convertAddUserDTOToUser(addUserDTO);
                userRepository.add(connection, user);
                user.getUserInformation().setUserId(user.getId());
                userInformationRepository.add(connection, user.getUserInformation());
                UserDTO addedUserDTO = convertUserToUserDTO(user);
                connection.commit();
                return addedUserDTO;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
                throw new SQLException("Add user failed");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Integer delete(Long id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Integer affectedUser = userRepository.delete(connection, id);
                connection.commit();
                return affectedUser;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
                throw new SQLException("Delete user failed");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private User convertAddUserDTOToUser(AddUserDTO addUserDTO) {
        User user = new User();

        user.setUserName(addUserDTO.getUserName());
        user.setPassword(addUserDTO.getPassword());
        user.setAge(addUserDTO.getAge());
        user.setIsActive(addUserDTO.getIsActive());

        UserInformation userInformation = new UserInformation();
        userInformation.setAddress(addUserDTO.getAddress());
        userInformation.setTelephone(addUserDTO.getTelephone());

        user.setUserInformation(userInformation);
        return user;
    }

    private List<UserDTO> convertUserListToUserDTOList(List<User> userList) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User userDB : userList) {
            UserDTO userDTO = convertUserToUserDTO(userDB);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    private UserDTO convertUserToUserDTO(User userDB) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(userDB.getId());
        userDTO.setUserName(userDB.getUserName());
        userDTO.setPassword(userDB.getPassword());
        userDTO.setAge(userDB.getAge());
        userDTO.setActive(userDB.getIsActive());
        userDTO.setAddress(userDB.getUserInformation().getAddress());
        userDTO.setTelephone(userDB.getUserInformation().getTelephone());

        return userDTO;
    }

}
