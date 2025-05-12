package com.uos.dsd.cinema.core.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import com.uos.dsd.cinema.integration.IntegrationTest;

public class FlywayConfigTest extends IntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.flyway.table}")
    private String flywayTable;

    @DisplayName("Flyway DB Migration Test")
    @Test
    public void flywayMigrationTest() {

        // get information from information_schema
        List<String> tableSchemas = jdbcTemplate.queryForList("SELECT DISTINCT table_schema FROM information_schema.tables", String.class);
        System.out.println("Table Schemas: " + tableSchemas);

        List<String> tableNames = jdbcTemplate.queryForList("SELECT table_name FROM information_schema.tables WHERE table_schema = 'PUBLIC'", String.class);
        System.out.println("Table Names: " + tableNames);

        // check flyway applied
        List<String> flywayVersionColumns = jdbcTemplate.queryForList(
                "SELECT column_name FROM information_schema.columns WHERE table_name = '" + flywayTable + "'",
                String.class);
        
        List<Map<String, Object>> flywayVersion =
                jdbcTemplate.queryForList("SELECT * FROM \"" + flywayTable + "\"");

        System.out.println("==flyway_schema_version==");
        System.out.println(flywayVersionColumns);
        flywayVersion.forEach(version -> {
            System.out.println(version.values());
        });
        assertThat(flywayVersion.size()).isGreaterThan(0);

        // check data count of specific table
        tableNames.forEach(tableName -> {
            int count = jdbcTemplate
                    .queryForObject("SELECT COUNT(*) FROM \"" 
                    + tableName + "\"", Integer.class);
            System.out.println(tableName + " Record Count: " + count);
        });
    }
}
