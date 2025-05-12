package com.uos.dsd.cinema;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.oracle.OracleContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    private static final String IMAGE_NAME = "gvenzl/oracle-free:latest";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "test";
    private static final String DATABASE_NAME = "test";

	@Bean
	@ServiceConnection
	OracleContainer oracleFreeContainer() {
		return new OracleContainer(DockerImageName.parse(IMAGE_NAME))
			.withUsername(USERNAME)
			.withPassword(PASSWORD)
			.withDatabaseName(DATABASE_NAME);
	}
}
