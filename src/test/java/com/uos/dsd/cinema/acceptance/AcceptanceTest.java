package com.uos.dsd.cinema.acceptance;

import com.uos.dsd.cinema.utils.DBInitializer;
import com.uos.dsd.cinema.utils.DBInitializeStrategy;
import com.uos.dsd.cinema.utils.OracleDBInitializeStrategy;
import com.uos.dsd.cinema.TestcontainersConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import org.junit.jupiter.api.AfterEach;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(
    listeners = {AcceptanceTestExecutionListener.class},
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@Import({
    DBInitializer.class,
    TestcontainersConfiguration.class, 
    AcceptanceTest.AcceptanceTestConfig.class
})
public abstract class AcceptanceTest {

    @Autowired
    DBInitializer dbInitializer;

    protected static final Logger log = LoggerFactory.getLogger(AcceptanceTest.class);

    @AfterEach
    public void clear() {

        dbInitializer.clear();
    }

    @TestConfiguration
    static class AcceptanceTestConfig {

        @Bean
        DBInitializeStrategy dbInitializeStrategy(@Autowired DataSource dataSource) {

            Resource initScript = new ClassPathResource("db/migration/V1__init.sql");
            return new OracleDBInitializeStrategy(dataSource, initScript);
        }
    }
}
