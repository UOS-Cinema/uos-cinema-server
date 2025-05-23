package com.uos.dsd.cinema.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

@TestComponent
public class DBInitializer {

    private final List<String> tableNames = new ArrayList<>();
    private final List<Resource> dataScripts = new ArrayList<>();
    private final Logger log = LoggerFactory.getLogger(DBInitializer.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DBInitializeStrategy dbInitializeStrategy;

    @PostConstruct
    public void init() {
        log.info("Initialize Database");
        dbInitializeStrategy.setEntityManager(entityManager);
        dbInitializeStrategy.createTable();
        setDataScripts();
        dbInitializeStrategy.createData(dataScripts);
        tableNames.addAll(dbInitializeStrategy.getTableNames());
        log.info("Initialize Database Completed");
    }

    @Transactional
    public void clear() {

        log.info("Clear Database");
        entityManager.clear();
        truncate();
        dbInitializeStrategy.createData(dataScripts);
        log.info("Clear Database Completed");
    }

    private void truncate() {

        log.info("Truncate Tables");
        setForeignKeyCheck(false);
        dbInitializeStrategy.truncateTables(tableNames);
        setForeignKeyCheck(true);
        log.info("Truncate Tables Completed");
    }

    private void setForeignKeyCheck(boolean check) {

        dbInitializeStrategy.setForeignKeyCheck(check);
    }

    private void setDataScripts() {

        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:db/*.sql");

            dataScripts.addAll(Arrays.asList(resources));

        } catch (IOException e) {
            throw new RuntimeException("Failed to load SQL files", e);
        }
    }
}
