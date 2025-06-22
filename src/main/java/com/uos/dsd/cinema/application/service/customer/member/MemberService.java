package com.uos.dsd.cinema.application.service.customer.member;

import com.uos.dsd.cinema.application.port.in.customer.member.command.DeleteMemberCommand;
import com.uos.dsd.cinema.application.port.in.customer.member.command.LoginMemberCommand;
import com.uos.dsd.cinema.application.port.in.customer.member.command.SignupMemberCommand;
import com.uos.dsd.cinema.application.port.in.customer.member.command.UpdateMemberCommand;
import com.uos.dsd.cinema.application.port.in.customer.member.query.GetMemberInfoQuery;
import com.uos.dsd.cinema.application.port.in.customer.member.response.MemberInfoResponse;
import com.uos.dsd.cinema.application.port.in.customer.member.usecase.DeleteMemberUsecase;
import com.uos.dsd.cinema.application.port.in.customer.member.usecase.GetMemberInfoUsecase;
import com.uos.dsd.cinema.application.port.in.customer.member.usecase.LoginMemberUsecase;
import com.uos.dsd.cinema.application.port.in.customer.member.usecase.SignupMemberUsecase;
import com.uos.dsd.cinema.application.port.in.customer.member.usecase.UpdateMemberUsecase;
import com.uos.dsd.cinema.application.port.out.customer.member.MemberRepository;
import com.uos.dsd.cinema.application.port.out.storage.Storage;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.common.exception.http.UnauthorizedException;
import com.uos.dsd.cinema.domain.customer.common.constraint.ProfileImageConstraint;
import com.uos.dsd.cinema.domain.customer.member.Member;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements SignupMemberUsecase, LoginMemberUsecase, UpdateMemberUsecase,
        GetMemberInfoUsecase, DeleteMemberUsecase {

    private final MemberRepository memberRepository;
    private final Storage storage;

    @Override
    @Transactional
    public Long signup(SignupMemberCommand command) {
        // 중복 username 검증 (soft-delete되지 않은 경우)
        if (memberRepository.existsByUsernameAndDeletedAtIsNull(command.username())) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Username already exists");
        }

        // 중복 phone 검증 (soft-delete되지 않은 경우)
        if (memberRepository.existsByPhoneAndDeletedAtIsNull(command.phone().replaceAll("-", ""))) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Phone number already exists");
        }

        // 프로필 이미지 검증
        ProfileImageConstraint.validateProfileImage(command.profileImage());

        try {
            // 프로필 사진 업로드 (선택사항)
            String profileImageUrl = null;
            if (command.profileImage() != null && !command.profileImage().isEmpty()) {
                String fileName = generateFileName(command.profileImage().getOriginalFilename());
                String filePath = generateMemberImagePath(command.username(), fileName);
                storage.upload(filePath, command.profileImage());
                profileImageUrl = storage.getUrl(filePath);
            }

            // 회원 생성 및 저장 (Customer 테이블 먼저 생성 후 Member 테이블 생성)
            // Member 생성자에서 Customer를 자동으로 생성하고 cascade로 함께 저장됨
            Member member = new Member(
                    command.username(),
                    command.password(),
                    command.name(),
                    command.phone(),
                    command.birthDate(),
                    profileImageUrl);

            Member savedMember = memberRepository.save(member);
            log.info("Member signup success, username: {}, id: {}", command.username(), savedMember.getId());
            return savedMember.getId();

        } catch (DataIntegrityViolationException e) {
            log.error("Member signup failed due to data integrity violation: {}", e.getMessage());
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Username or phone number already exists");
        } catch (Exception e) {
            log.error("Member signup failed for username: {}", command.username(), e);
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Signup failed");
        }
    }

    @Override
    public Long login(LoginMemberCommand command) {
        // username으로 회원 조회 (soft-delete되지 않은 경우)
        Member member = memberRepository.findByUsernameAndDeletedAtIsNull(command.username())
                .orElseThrow(() -> new UnauthorizedException(CommonResultCode.UNAUTHORIZED,
                        "Invalid username or password"));

        // 비밀번호 검증
        if (!member.isPasswordMatched(command.password())) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED, "Invalid username or password");
        }

        log.info("Member login success, username: {}, id: {}", command.username(), member.getId());
        return member.getId();
    }

    @Override
    @Transactional
    public void updateMember(UpdateMemberCommand command) {
        // 회원 조회 (soft-delete되지 않은 경우)
        Member member = memberRepository.findByUsernameAndDeletedAtIsNull(command.username())
                .orElseThrow(() -> new NotFoundException(CommonResultCode.NOT_FOUND, "Member not found"));

        // 현재 비밀번호 검증
        if (!member.isPasswordMatched(command.password())) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED, "Invalid password");
        }

        // 프로필 이미지 검증
        ProfileImageConstraint.validateProfileImage(command.profileImage());

        try {
            String newProfileImageUrl = member.getProfileImageUrl(); // 기존 URL 유지

            // 프로필 사진이 변경된 경우
            if (command.profileImage() != null && !command.profileImage().isEmpty()) {
                // 새 이미지 업로드
                String fileName = generateFileName(command.profileImage().getOriginalFilename());
                String filePath = generateMemberImagePath(command.username(), fileName);
                storage.upload(filePath, command.profileImage());
                newProfileImageUrl = storage.getUrl(filePath);

                // 기존 이미지 삭제
                if (member.getProfileImageUrl() != null) {
                    try {
                        String oldFilePath = extractFilePathFromUrl(member.getProfileImageUrl());
                        storage.delete(oldFilePath);
                    } catch (Exception e) {
                        log.warn("Failed to delete old profile image for user: {}", command.username(), e);
                    }
                }
            }

            // 회원 정보 업데이트
            member.updateInfo(command.name(), command.phone(), command.birthDate(), newProfileImageUrl);

            // 새 비밀번호가 제공된 경우 비밀번호 업데이트
            if (command.newPassword() != null && !command.newPassword().isEmpty()) {
                member.updatePassword(command.newPassword());
            }

            memberRepository.save(member);
            log.info("Member update success, username: {}", command.username());

        } catch (DataIntegrityViolationException e) {
            log.error("Member update failed due to data integrity violation: {}", e.getMessage());
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Phone number already exists");
        } catch (Exception e) {
            log.error("Member update failed for username: {}", command.username(), e);
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Update failed");
        }
    }

    @Override
    public MemberInfoResponse getMemberInfo(GetMemberInfoQuery query) {
        // 회원 조회 (soft-delete되지 않은 경우)
        Member member = memberRepository.findByUsernameAndDeletedAtIsNull(query.username())
                .orElseThrow(() -> new NotFoundException(CommonResultCode.NOT_FOUND, "Member not found"));

        return new MemberInfoResponse(
                member.getUsername(),
                member.getName(),
                member.getPhone(),
                member.getBirthDate(),
                member.getProfileImageUrl());
    }

    @Override
    @Transactional
    public void deleteMember(DeleteMemberCommand command) {
        // 회원 조회 (soft-delete되지 않은 경우)
        Member member = memberRepository.findByUsernameAndDeletedAtIsNull(command.username())
                .orElseThrow(() -> new NotFoundException(CommonResultCode.NOT_FOUND, "Member not found"));

        // 비밀번호 검증
        if (!member.isPasswordMatched(command.password())) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED, "Invalid password");
        }

        try {
            // 프로필 이미지 삭제
            if (member.getProfileImageUrl() != null) {
                try {
                    String filePath = extractFilePathFromUrl(member.getProfileImageUrl());
                    storage.delete(filePath);
                } catch (Exception e) {
                    log.warn("Failed to delete profile image during member deletion for user: {}", command.username(), e);
                }
            }

            // 회원 soft delete
            member.delete();
            memberRepository.save(member);
            log.info("Member delete success, username: {}", command.username());

        } catch (Exception e) {
            log.error("Member deletion failed for username: {}", command.username(), e);
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Deletion failed");
        }
    }

    private String generateFileName(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }

    private String generateMemberImagePath(String username, String fileName) {
        return Paths.get("members", username, "profile", fileName).toString();
    }

    private String extractFilePathFromUrl(String url) {
        // URL format: http://domain:port/files/members/username/profile/filename
        // Extract: members/username/profile/filename
        String[] parts = url.split("/files/");
        if (parts.length > 1) {
            return parts[1];
        }
        throw new IllegalArgumentException("Invalid file URL format: " + url);
    }
}
