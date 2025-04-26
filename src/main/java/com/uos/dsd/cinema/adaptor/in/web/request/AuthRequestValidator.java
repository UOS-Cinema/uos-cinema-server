package com.uos.dsd.cinema.adaptor.in.web.request;

import com.uos.dsd.cinema.adaptor.in.web.request.AuthController.AdminLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.request.AuthController.AdminSignupRequest;
import com.uos.dsd.cinema.adaptor.in.web.request.AuthController.GuestLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.request.AuthController.MemberLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.request.AuthController.MemberSignupRequest;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AuthRequestValidator {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_ID_LENGTH = 20;
    private static final String PHONE_REGEX = "^\\d{3}-\\d{3,4}-\\d{4}$";

    public void validateMemberLoginRequest(MemberLoginRequest request) {
        if (request == null) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "요청 정보가 없습니다");
        }
        
        if (!StringUtils.hasText(request.memberId())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "아이디는 필수 입력 항목입니다");
        }
        
        if (!StringUtils.hasText(request.password())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "비밀번호는 필수 입력 항목입니다");
        }
    }
    
    public void validateGuestLoginRequest(GuestLoginRequest request) {
        if (request == null) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "요청 정보가 없습니다");
        }
        
        if (!StringUtils.hasText(request.name())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "이름은 필수 입력 항목입니다");
        }
        
        if (!StringUtils.hasText(request.phone())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "전화번호는 필수 입력 항목입니다");
        }
        
        if (!request.phone().matches(PHONE_REGEX)) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "전화번호 형식이 올바르지 않습니다");
        }
        
        if (request.birthDate() == null) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "생년월일은 필수 입력 항목입니다");
        }
        
        if (!StringUtils.hasText(request.password())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "비밀번호는 필수 입력 항목입니다");
        }
    }
    
    public void validateAdminLoginRequest(AdminLoginRequest request) {
        if (request == null) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "요청 정보가 없습니다");
        }
        
        if (!StringUtils.hasText(request.name())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "이름은 필수 입력 항목입니다");
        }
        
        if (!StringUtils.hasText(request.password())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "비밀번호는 필수 입력 항목입니다");
        }
    }
    
    public void validateMemberSignupRequest(MemberSignupRequest request) {
        if (request == null) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "요청 정보가 없습니다");
        }
        
        if (!StringUtils.hasText(request.memberId())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "아이디는 필수 입력 항목입니다");
        }
        
        if (request.memberId().length() > MAX_ID_LENGTH) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "아이디는 최대 " + MAX_ID_LENGTH + "자까지 입력 가능합니다");
        }
        
        if (!StringUtils.hasText(request.password())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "비밀번호는 필수 입력 항목입니다");
        }
        
        if (request.password().length() < MIN_PASSWORD_LENGTH) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "비밀번호는 최소 " + MIN_PASSWORD_LENGTH + "자 이상이어야 합니다");
        }
        
        if (!StringUtils.hasText(request.name())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "이름은 필수 입력 항목입니다");
        }
        
        if (!StringUtils.hasText(request.phone())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "전화번호는 필수 입력 항목입니다");
        }
        
        if (!request.phone().matches(PHONE_REGEX)) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "전화번호 형식이 올바르지 않습니다");
        }
        
        if (request.birthDate() == null) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "생년월일은 필수 입력 항목입니다");
        }
    }
    
    public void validateAdminSignupRequest(AdminSignupRequest request) {
        if (request == null) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "요청 정보가 없습니다");
        }
        
        if (!StringUtils.hasText(request.name())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "이름은 필수 입력 항목입니다");
        }
        
        if (!StringUtils.hasText(request.password())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "비밀번호는 필수 입력 항목입니다");
        }
        
        if (request.password().length() < MIN_PASSWORD_LENGTH) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "비밀번호는 최소 " + MIN_PASSWORD_LENGTH + "자 이상이어야 합니다");
        }
    }
} 
