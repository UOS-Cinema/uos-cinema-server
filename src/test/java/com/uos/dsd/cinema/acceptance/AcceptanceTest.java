package com.uos.dsd.cinema.acceptance;

import com.uos.dsd.cinema.utils.DBInitializer;
import com.uos.dsd.cinema.utils.DBInitializeStrategy;
import com.uos.dsd.cinema.utils.OracleDBInitializeStrategy;
import com.uos.dsd.cinema.TestcontainersConfiguration;
import com.uos.dsd.cinema.acceptance.admin.steps.AdminSteps;
import com.uos.dsd.cinema.acceptance.customer.guest.steps.GuestSteps;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.customer.guest.request.GuestLoginRequest;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

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
import io.restassured.response.Response;

import javax.sql.DataSource;
import java.time.LocalDate;

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

    protected String loginAdmin() {
        AdminLoginRequest request = new AdminLoginRequest(
            "administrator",
            "password123!"
        );

        Response response = AdminSteps.sendLoginAdmin(AuthHeaderProvider.createEmptyHeader(), request);
        return response.jsonPath().getString("data.accessToken");
    }

    protected String loginGuest() {
        GuestLoginRequest request = new GuestLoginRequest(
            "비회원2",
            "010-9999-9902",
            LocalDate.of(1998, 1, 8),
            "password123!"
        );

        Response response = GuestSteps.sendLoginGuest(AuthHeaderProvider.createEmptyHeader(), request);
        return response.jsonPath().getString("data.accessToken");
    }
}
