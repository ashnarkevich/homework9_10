package com.gmail.petrikov05.app.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.petrikov05.app.repository.ConnectionRepository;
import com.gmail.petrikov05.app.repository.TableRepository;
import com.gmail.petrikov05.app.repository.impl.ConnectionRepositoryImpl;
import com.gmail.petrikov05.app.repository.impl.TableRepositoryImpl;
import com.gmail.petrikov05.app.service.TableService;
import com.gmail.petrikov05.app.service.constant.CreateActionEnum;
import com.gmail.petrikov05.app.service.constant.DropActionEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TableServiceImpl implements TableService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static TableService instance;
    private ConnectionRepository connectionRepository;
    private TableRepository tableRepository;

    private TableServiceImpl(ConnectionRepository connectionRepository, TableRepository tableRepository) {
        this.connectionRepository = connectionRepository;
        this.tableRepository = tableRepository;
    }

    public static TableService getInstance() {
        if (instance == null) {
            instance = new TableServiceImpl(ConnectionRepositoryImpl.getInstance(), TableRepositoryImpl.getInstance());
        }
        return instance;
    }

    @Override
    public void dropTable() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (DropActionEnum action : DropActionEnum.values()) {
                    tableRepository.executeQuery(connection, action.getQuery());
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new SQLException("Delete table failed");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

    }

    @Override
    public void createTable() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (CreateActionEnum action : CreateActionEnum.values()) {
                    tableRepository.executeQuery(connection, action.getQuery());
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new SQLException("Create table failed");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
