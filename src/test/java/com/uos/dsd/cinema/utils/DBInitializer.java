package com.uos.dsd.cinema.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@TestComponent
public class DBInitializer {

    private final List<String> tableNames = new ArrayList<>();

    private final Logger log = LoggerFactory.getLogger(DBInitializer.class);

    @Autowired
    private DataSource dataSource;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DBInitializeStrategy dbInitializeStrategy;

    @PostConstruct
    public void init() {

        dbInitializeStrategy.setEntityManager(entityManager);
        dbInitializeStrategy.createTable();
        dbInitializeStrategy.createData();
        findDatabaseTableNames();
        log.debug("tableNames: {}", tableNames);
    }

    @Transactional
    public void clear() {

        entityManager.clear();
        truncate();
        dbInitializeStrategy.createData();
    }

    private void findDatabaseTableNames() {

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, "PUBLIC", "%", new String[] {"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                tableNames.add(tableName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception occurred while finding database table names", e);
        }
    }

    private void truncate() {

        setForeignKeyCheck(false);
        dbInitializeStrategy.truncateTables(tableNames);
        setForeignKeyCheck(true);
    }

    private void setForeignKeyCheck(boolean check) {

        dbInitializeStrategy.setForeignKeyCheck(check);
    }
}
