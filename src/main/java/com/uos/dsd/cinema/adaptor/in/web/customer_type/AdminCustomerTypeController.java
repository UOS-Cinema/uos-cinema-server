package com.uos.dsd.cinema.adaptor.in.web.customer_type;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.dsd.cinema.adaptor.in.web.customer_type.request.CreateCustomerTypeRequest;
import com.uos.dsd.cinema.adaptor.in.web.customer_type.request.UpdateCustomerTypeRequest;
import com.uos.dsd.cinema.adaptor.out.persistence.customer_type.CustomerTypeJpaRepository;
import com.uos.dsd.cinema.application.registry.CustomerTypeRegistry;
import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.customer_type.CustomerType;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/customer-types")
public class AdminCustomerTypeController {

    private final CustomerTypeJpaRepository customerTypeJpaRepository;
    private final CustomerTypeRegistry customerTypeRegistry;

    @PostMapping
    public ApiResponse<String> createCustomerType(
        @UserRole Role role,
            @RequestBody CreateCustomerTypeRequest request) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        return ApiResponse.success(createCustomerType(request).getType());
    }

    @GetMapping
    public ApiResponse<List<CustomerType>> getAllCustomerTypes(
            @UserRole Role role) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        return ApiResponse.success(customerTypeRegistry.getAll());
    }

    @PutMapping("/{type}")
    public ApiResponse<String> updateCustomerType(
        @UserRole Role role,
        @PathVariable String type,
        @RequestBody UpdateCustomerTypeRequest request) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        return ApiResponse.success(updateCustomerType(type, request).getType());
    }

    @DeleteMapping("/{type}")
    public ApiResponse<Void> deleteCustomerType(
        @UserRole Role role,
        @PathVariable String type) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        deleteScreenType(type);
        return ApiResponse.success();
    }

    @Transactional
    private CustomerType updateCustomerType(String type, UpdateCustomerTypeRequest request) {
        CustomerType customerType = customerTypeRegistry.get(type);
        if (customerType == null) {
            throw new NotFoundException();
        }
        customerType.modifyDiscountAmount(request.discountAmount());
        customerTypeJpaRepository.save(customerType);
        customerTypeRegistry.reload(null);
        return customerType;
    }

    @Transactional
    private CustomerType createCustomerType(CreateCustomerTypeRequest request) {
        CustomerType customerType =
                new CustomerType(request.type(), request.discountAmount());
        customerTypeJpaRepository.save(customerType);
        customerTypeRegistry.reload(null);
        return customerType;
    }

    // Screen Type이 사용중인 극장이나 영화가 있으면 삭제 불가능
    @Transactional
    private void deleteScreenType(String type) {
        CustomerType customerType = customerTypeRegistry.get(type);
        if (customerType == null) {
            throw new NotFoundException();
        }
        customerTypeJpaRepository.delete(customerType);
        customerTypeRegistry.reload(null);
    }
}
