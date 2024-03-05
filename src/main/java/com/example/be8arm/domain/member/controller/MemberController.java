package com.example.be8arm.domain.member.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.be8arm.domain.member.member.dto.JwtToken;
import com.example.be8arm.domain.member.member.dto.LoginDto;
import com.example.be8arm.domain.member.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/login")
	public JwtToken logIn(@RequestBody LoginDto loginDto) {
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
}
