package com.uos.dsd.cinema.acceptance;

import com.uos.dsd.cinema.TestcontainersConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(
    listeners = {AcceptanceTestExecutionListener.class},
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@Testcontainers
@Import(TestcontainersConfiguration.class)
public abstract class AcceptanceTest {

    protected static final Logger log = LoggerFactory.getLogger(AcceptanceTest.class);
}
