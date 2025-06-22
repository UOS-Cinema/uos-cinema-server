package com.uos.dsd.cinema.adaptor.in.web.customer.member;

import com.uos.dsd.cinema.adaptor.in.web.customer.member.request.MemberDeleteRequest;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.request.MemberLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.request.MemberSignupRequest;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.request.MemberUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.response.GetMemberInfoResponse;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.response.MemberLoginResponse;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.response.MemberSignupResponse;
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
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.utils.CookieUtil;
import com.uos.dsd.cinema.core.annotation.UserId;
import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.jwt.JwtUtils;
import com.uos.dsd.cinema.core.security.SecurityConstants;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.domain.customer.member.Member;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final SignupMemberUsecase signupMemberUsecase;
    private final LoginMemberUsecase loginMemberUsecase;
    private final UpdateMemberUsecase updateMemberUsecase;
    private final GetMemberInfoUsecase getMemberInfoUsecase;
    private final DeleteMemberUsecase deleteMemberUsecase;
    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ApiResponse<MemberSignupResponse> signup(
            @Valid @ModelAttribute MemberSignupRequest request) {

        Long id = signupMemberUsecase.signup(
                new SignupMemberCommand(
                        request.username(),
                        request.password(),
                        request.name(),
                        request.phone(),
                        java.time.LocalDate.parse(request.birthDate()),
                        request.profileImage()));

        log.info("member signup success, username: {}", request.username());
        return ApiResponse.success(new MemberSignupResponse(id, request.username()));
    }

    @PostMapping("/login")
    public ApiResponse<MemberLoginResponse> login(
            @Valid @RequestBody MemberLoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse response) {

        Long id = loginMemberUsecase.login(
                new LoginMemberCommand(
                        request.username(),
                        request.password()));

        String accessToken = jwtUtils.generateAccessToken(id, Role.MEMBER);
        String refreshToken = jwtUtils.generateRefreshToken(id, Role.MEMBER);
        CookieUtil.addHttpOnlyCookie(response, SecurityConstants.REISSUE_COOKIE_NAME, refreshToken,
                jwtUtils.getRefreshTokenExpirationMs(), "/auth", httpRequest.isSecure());

        log.info("member login success, username: {}", request.username());
        return ApiResponse.success(new MemberLoginResponse(accessToken));
    }

    @PutMapping("/{username}")
    public ApiResponse<Void> updateMember(
            @UserId Long requesterId,
            @UserRole Role requesterRole,
            @PathVariable("username") String username,
            @Valid @ModelAttribute MemberUpdateRequest request) {

        // 권한 검증
        if (requesterRole != Role.MEMBER) {
            throw new ForbiddenException(CommonResultCode.FORBIDDEN, "Member role required");
        }

        // 현재 로그인한 사용자의 username과 요청하는 username이 같은지 확인
        Member requestingMember = memberRepository.findByIdAndDeletedAtIsNull(requesterId)
                .orElseThrow(() -> new NotFoundException(CommonResultCode.NOT_FOUND, "Member not found"));
        
        if (!requestingMember.getUsername().equals(username)) {
            throw new ForbiddenException(CommonResultCode.FORBIDDEN, "You can only update your own info");
        }

        updateMemberUsecase.updateMember(
                new UpdateMemberCommand(
                        username,
                        request.password(),
                        request.newPassword(),
                        request.name(),
                        request.phone(),
                        request.birthDate() != null ? java.time.LocalDate.parse(request.birthDate()) : null,
                        request.profileImage()));

        log.info("member update success, username: {}", username);
        return ApiResponse.success();
    }

    @GetMapping("/{username}")
    public ApiResponse<GetMemberInfoResponse> getMemberInfo(
            @UserId Long requesterId,
            @UserRole Role requesterRole,
            @PathVariable("username") String username) {

        // 권한 검증
        if (requesterRole != Role.MEMBER) {
            throw new ForbiddenException(CommonResultCode.FORBIDDEN, "Member role required");
        }

        // 현재 로그인한 사용자의 username과 요청하는 username이 같은지 확인
        Member requestingMember = memberRepository.findByIdAndDeletedAtIsNull(requesterId)
                .orElseThrow(() -> new NotFoundException(CommonResultCode.NOT_FOUND, "Member not found"));
        
        if (!requestingMember.getUsername().equals(username)) {
            throw new ForbiddenException(CommonResultCode.FORBIDDEN, "You can only get your own info");
        }

        MemberInfoResponse member = getMemberInfoUsecase.getMemberInfo(new GetMemberInfoQuery(username));
        GetMemberInfoResponse response = new GetMemberInfoResponse(
                member.username(),
                member.name(),
                member.phone(),
                member.birthDate(),
                member.profileImageUrl());

        log.info("get member info success, username: {}", username);
        return ApiResponse.success(response);
    }

    @DeleteMapping("/{username}")
    public ApiResponse<Void> deleteMember(
            @UserId Long requesterId,
            @UserRole Role requesterRole,
            @PathVariable("username") String username,
            @Valid @RequestBody MemberDeleteRequest request) {

        // 권한 검증
        if (requesterRole != Role.MEMBER) {
            throw new ForbiddenException(CommonResultCode.FORBIDDEN, "Member role required");
        }

        // 현재 로그인한 사용자의 username과 요청하는 username이 같은지 확인
        Member requestingMember = memberRepository.findByIdAndDeletedAtIsNull(requesterId)
                .orElseThrow(() -> new NotFoundException(CommonResultCode.NOT_FOUND, "Member not found"));
        
        if (!requestingMember.getUsername().equals(username)) {
            throw new ForbiddenException(CommonResultCode.FORBIDDEN, "You can only delete your own account");
        }

        deleteMemberUsecase.deleteMember(new DeleteMemberCommand(username, request.password()));

        log.info("member delete success, username: {}", username);
        return ApiResponse.success();
    }
}
