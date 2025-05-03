package com.uos.dsd.cinema.acceptance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;

import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.AfterEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uos.dsd.cinema.utils.DBInitializer;
import com.uos.dsd.cinema.TestcontainersConfiguration;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(
    listeners = {AcceptanceTestExecutionListener.class},
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@Testcontainers
@Import({DBInitializer.class, TestcontainersConfiguration.class})
public abstract class AcceptanceTest {

    @Autowired
    DBInitializer dbInitializer;

    protected static final Logger log = LoggerFactory.getLogger(AcceptanceTest.class);

    @AfterEach
    public void clear() {

        dbInitializer.clear();
    }
}
