package com.example.be8arm.domain.member.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.be8arm.domain.member.member.dto.LogInDto;
import com.example.be8arm.domain.member.member.dto.MemberDto;
import com.example.be8arm.domain.member.member.dto.SignUpDto;
import com.example.be8arm.domain.member.member.service.MemberService;
import com.example.be8arm.global.jwt.JwtToken;
import com.example.be8arm.global.jwt.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/join")
	public ResponseEntity<MemberDto> signUp(@RequestBody SignUpDto signUpDto) {
		MemberDto savedMemberDto = memberService.signUp(signUpDto);
		return ResponseEntity.ok(savedMemberDto);
	}

	@PostMapping("/login")
	public JwtToken logIn(@RequestBody LogInDto loginDto) {
		String username = loginDto.getUsername();
		String password = loginDto.getPassword();
		JwtToken jwtToken = memberService.logIn(username, password);
		log.info("request username = {}, password = {}", username, password);
		log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
		return jwtToken;
	}

	@PostMapping("/test")
	public String test() {
		return SecurityUtil.getCurrentUsername();
	}

	public class SecurityUtil {
		public static String getCurrentUsername() {
			final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || authentication.getName() == null) {
				throw new RuntimeException("No authentication information.");
			}
			return authentication.getName();
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logOut(HttpServletRequest request) {
		jwtTokenProvider.invalidateToken(request);
		return ResponseEntity.ok("로그아웃 성공");
	}
}