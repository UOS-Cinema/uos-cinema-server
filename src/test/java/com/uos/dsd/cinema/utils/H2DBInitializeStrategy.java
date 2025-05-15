package com.uos.dsd.cinema.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class H2DBInitializeStrategy implements DBInitializeStrategy {

    private static final Logger log = LoggerFactory.getLogger(H2DBInitializeStrategy.class);

    private static final Resource initScript = new ClassPathResource("db/h2/migration/V1__init.sql");
    private static final List<Resource> dataScripts = new ArrayList<>();

    private final DataSource dataSource;
    private EntityManager entityManager;

    static {

        // insert data considering foreign key constraint
        dataScripts.add(new ClassPathResource("db/screen_type.sql"));
        dataScripts.add(new ClassPathResource("db/theater.sql"));
    }

    public H2DBInitializeStrategy(DataSource dataSource) {

        this.dataSource = dataSource;
        entityManager = null;
    }

    @Override
    public List<String> getTableNames() {

        try (Connection connection = dataSource.getConnection()) {
            List<String> tableNames = new ArrayList<>();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, "PUBLIC", "%", new String[] {"TABLE"});

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

        log.info("[H2DBInitializeStrategy] truncateTables");
        try (Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement()) {

            stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");
            for (String tableName : tableNames) {
                stmt.executeUpdate("TRUNCATE TABLE " + tableName);
            }
            stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");

        } catch (SQLException e) {
            throw new RuntimeException("Exception occurred while truncating tables in H2", e);
        }
        log.info("[H2DBInitializeStrategy] truncateTables completed");
    }

    @Override
    public void setForeignKeyCheck(boolean check) {

        log.info("[H2DBInitializeStrategy] setForeignKeyCheck");
        try (Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement()) {

            if (check) {
                stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");
            } else {
                stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to set foreign key check in H2", e);
        }
        log.info("[H2DBInitializeStrategy] setForeignKeyCheck completed");
    }

    @Override
    public void createTable() {

        log.info("[H2DBInitializeStrategy] createTable");
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, initScript);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create tables from V1__init.sql", e);
        }
        log.info("[H2DBInitializeStrategy] createTable completed");
    }

    @Override
    @Transactional
    public void createData() {

        log.info("[H2DBInitializeStrategy] createData");
        try (Connection connection = dataSource.getConnection()) {
            for (Resource script : dataScripts) {
                ScriptUtils.executeSqlScript(connection, script);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create data", e);
        }
        log.info("[H2DBInitializeStrategy] createData completed");
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {

        if (this.entityManager != null) {
            throw new RuntimeException("EntityManager is already set");
        }
        this.entityManager = entityManager;
    }
}
