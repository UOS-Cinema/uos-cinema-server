package com.uos.dsd.cinema.acceptance;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import io.restassured.RestAssured;

public class AcceptanceTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestClass(final TestContext testContext) {

        final Integer serverPort = testContext.getApplicationContext().getEnvironment()
                .getProperty("local.server.port", Integer.class);
        if (serverPort == null) {
            throw new IllegalStateException("localServerPort cannot be null");
        }

        RestAssured.port = serverPort;
    }
}
