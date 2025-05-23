package com.uos.dsd.cinema.integration;

import com.uos.dsd.cinema.utils.DBInitializer;
import com.uos.dsd.cinema.utils.DBInitializeStrategy;
import com.uos.dsd.cinema.utils.H2DBInitializeStrategy;
import com.uos.dsd.cinema.application.registry.ScreenTypeRegistry;
import com.uos.dsd.cinema.application.port.out.screen_type.ScreenTypeRepository;
import com.uos.dsd.cinema.application.registry.GenreRegistry;
import com.uos.dsd.cinema.application.port.out.genre.GenreRepository;
import com.uos.dsd.cinema.application.registry.BankRegistry;
import com.uos.dsd.cinema.application.port.out.affiliate.BankRepository;
import com.uos.dsd.cinema.application.registry.CardCompanyRegistry;
import com.uos.dsd.cinema.application.port.out.affiliate.CardCompanyRepository;
import com.uos.dsd.cinema.application.port.out.customer_type.CustomerTypeRepository;
import com.uos.dsd.cinema.application.registry.CustomerTypeRegistry;

import org.springframework.context.annotation.Import;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

import org.junit.jupiter.api.AfterEach;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Import({DBInitializer.class, IntegrationTest.IntegrationTestConfig.class})
public abstract class IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected DBInitializer dbInitializer;

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    @Autowired
    protected ScreenTypeRegistry screenTypeRegistry;

    @Autowired
    protected ScreenTypeRepository screenTypeRepository;

    @Autowired
    protected GenreRegistry genreRegistry;

    @Autowired
    protected GenreRepository genreRepository;

    @Autowired
    protected BankRegistry bankRegistry;

    @Autowired
    protected BankRepository bankRepository;

    @Autowired
    protected CardCompanyRegistry cardCompanyRegistry;

    @Autowired
    protected CardCompanyRepository cardCompanyRepository;

    @Autowired
    protected CustomerTypeRegistry customerTypeRegistry;

    @Autowired
    protected CustomerTypeRepository customerTypeRepository;

    protected Logger log = LoggerFactory.getLogger(IntegrationTest.class);

    @AfterEach
    public void clear() {

        dbInitializer.clear();
    }

    @TestConfiguration
    static class IntegrationTestConfig {

        @Bean
        DBInitializeStrategy dbInitializeStrategy(@Autowired DataSource dataSource) {

            Resource initScript = new ClassPathResource("db/h2/migration/V1__init.sql");
            return new H2DBInitializeStrategy(dataSource, initScript);
        }
    }
}
