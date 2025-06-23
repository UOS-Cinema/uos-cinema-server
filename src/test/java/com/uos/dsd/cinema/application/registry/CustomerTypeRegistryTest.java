package com.uos.dsd.cinema.application.registry;

import static org.assertj.core.api.Assertions.assertThat;

import com.uos.dsd.cinema.integration.IntegrationTest;
import com.uos.dsd.cinema.domain.customer_type.CustomerType;
import com.uos.dsd.cinema.domain.customer_type.CustomerTypeReloadEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.List;

public class CustomerTypeRegistryTest extends IntegrationTest {

    @AfterEach
    public void reloadCustomerTypeRegistry() {

        customerTypeRegistry.reload(new CustomerTypeReloadEvent());
    }

    @Test
    public void customerTypeRegistryInitTest() {

        // when
        List<CustomerType> customerTypes = customerTypeRegistry.getAll();
        log.info("customerTypes: {}", customerTypes);

        // then
        assertThat(customerTypes.size()).isGreaterThan(0);
    }

    @Test
    public void customerTypeRegistryGetTest() {

        // when
        CustomerType customerType = customerTypeRegistry.get("ADULT");

        // then
        assertThat(customerType.getType()).isEqualTo("ADULT");
    }

    @Test
    public void customerTypeRegistryReloadTestWhenAdded() throws InterruptedException {

        // given
        int size = customerTypeRegistry.getAll().size();
        CustomerType customerType = new CustomerType("NEW", 0);
        customerTypeRepository.save(customerType);

        // when
        CustomerTypeReloadEvent event = new CustomerTypeReloadEvent();
        eventPublisher.publishEvent(event);

        Thread.sleep(100);

        // then
        List<CustomerType> customerTypes = customerTypeRegistry.getAll();
        log.info("customerTypes: {}", customerTypes);
        assertThat(customerTypes.size()).isEqualTo(size + 1);
    }
}
