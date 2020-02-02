package com.gmail.petrikov05.app.repository.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.gmail.petrikov05.app.repository.UserInformationRepository;
import com.gmail.petrikov05.app.repository.model.UserInformation;

public class UserInformationRepositoryImpl extends GeneralRepositoryImpl<UserInformation> implements UserInformationRepository {

    private static UserInformationRepository instance;

    private UserInformationRepositoryImpl() {
    }

    public static UserInformationRepository getInstance() {
        if (instance == null) {
            instance = new UserInformationRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<UserInformation> findAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserInformation add(Connection connection, UserInformation userInformation) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO user_information(user_id, telephone, address) VALUES (?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setLong(1, userInformation.getUserId());
            statement.setString(2, userInformation.getTelephone());
            statement.setString(3, userInformation.getAddress());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating userInformation failed, no rows affected.");
            }
            return userInformation;
        }
    }

    @Override
    public Integer delete(Connection connection, Serializable id) throws SQLException {
        throw new UnsupportedOperationException();
    }

}
