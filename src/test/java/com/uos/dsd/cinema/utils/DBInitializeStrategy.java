package com.uos.dsd.cinema.utils;

import org.springframework.core.io.Resource;

import jakarta.persistence.EntityManager;

import java.util.List;

public interface DBInitializeStrategy {

    List<String> getTableNames();

    void truncateTables(List<String> tableNames);

    void setForeignKeyCheck(boolean check);

    void createTable();

    void createData(List<Resource> dataScripts);

    void setEntityManager(EntityManager entityManager);
}
