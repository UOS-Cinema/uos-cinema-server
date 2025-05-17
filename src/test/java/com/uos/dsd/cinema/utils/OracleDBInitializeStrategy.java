package com.uos.dsd.cinema.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.util.List;
import java.util.ArrayList;

public class OracleDBInitializeStrategy implements DBInitializeStrategy {

    private static final Logger log = LoggerFactory.getLogger(OracleDBInitializeStrategy.class);

    private static final Resource initScript = new ClassPathResource("db/migration/V1__init.sql");
    private static final List<Resource> dataScripts = new ArrayList<>();

    private final DataSource dataSource;
    private EntityManager entityManager;

    static {

        // insert data considering foreign key constraint
        dataScripts.add(new ClassPathResource("db/screen_type.sql"));
        dataScripts.add(new ClassPathResource("db/theater.sql"));
    }

    public OracleDBInitializeStrategy(DataSource dataSource) {

        this.dataSource = dataSource;
        this.entityManager = null;
    }

    @Override
    public List<String> getTableNames() {

        try (Connection connection = dataSource.getConnection()) {
            List<String> tableNames = new ArrayList<>();
            DatabaseMetaData metaData = connection.getMetaData();
            String currentUser = metaData.getUserName();
            ResultSet tables = metaData.getTables(null, currentUser, "%", new String[] {"TABLE"});
            log.info("Current User: {}", currentUser);

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                tableNames.add(tableName);
            }
            log.info("Table Names: {}", tableNames);

            return tableNames;
        } catch (SQLException e) {
            throw new RuntimeException("Exception occurred while finding database table names", e);
        }
    }

    @Override
    public void truncateTables(List<String> tableNames) {

        log.info("[OracleDBInitializeStrategy] truncateTables");
        try (Connection connection = dataSource.getConnection()) {
            for (String tableName : tableNames) {
                connection.createStatement().executeUpdate("TRUNCATE TABLE " + tableName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception occurred while truncating tables", e);
        }
    }

    @Override
    public void setForeignKeyCheck(boolean check) {

        log.info("[OracleDBInitializeStrategy] setForeignKeyCheck");
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet rs;

            if (check) {
                rs = statement.executeQuery("""
                    SELECT 'ALTER TABLE ' || table_name || ' ENABLE CONSTRAINT ' || constraint_name
                    FROM user_constraints
                    WHERE constraint_type = 'R'
                """);
            } else {
                rs = statement.executeQuery("""
                    SELECT 'ALTER TABLE ' || table_name || ' DISABLE CONSTRAINT ' || constraint_name
                    FROM user_constraints
                    WHERE constraint_type = 'R'
                """);
            }

            try (Statement execStmt = connection.createStatement()) {
                while (rs.next()) {
                    String query = rs.getString(1);
                    execStmt.executeUpdate(query);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to set foreign key check", e);
        }
    }

    @Override
    public void createTable() {

        log.info("[OracleDBInitializeStrategy] createTable");
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, initScript);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create tables from V1__init.sql", e);
        }
    }

    @Override
    @Transactional
    public void createData() {

        log.info("[OracleDBInitializeStrategy] createData");
        try (Connection connection = dataSource.getConnection()) {
            for (Resource script : dataScripts) {
                ScriptUtils.executeSqlScript(connection, script);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create data", e);
        }
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {

        if (this.entityManager != null) {
            throw new RuntimeException("EntityManager is already set");
        }
        this.entityManager = entityManager;
    }
}
