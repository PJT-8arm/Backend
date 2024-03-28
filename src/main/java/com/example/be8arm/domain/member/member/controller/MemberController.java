package com.example.be8arm.domain.member.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/join")
	public ResponseEntity<MemberDto> signUp(@RequestBody SignUpDto signUpDto) {
		MemberDto savedMemberDto = memberService.signUp(signUpDto);
		return ResponseEntity.ok(savedMemberDto);
	}

	@PostMapping("/login")
	public ResponseEntity<String> logIn(@RequestBody LogInDto loginDto, HttpServletResponse response) {
		try {
			String username = loginDto.getUsername();
			String password = loginDto.getPassword();

			JwtToken jwtToken = memberService.logIn(username, password, response);
			log.info("request username = {}, password = {}", username, password);
			log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(),
				jwtToken.getRefreshToken());

			return ResponseEntity.ok("로그인 성공");

		} catch (AuthenticationException e) {
			// 아이디나 비밀번호가 잘못된 경우 처리
			return ResponseEntity.badRequest().body("아이디 또는 비밀번호를 올바르게 입력해주세요");
		}
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
	public ResponseEntity<String> logOut(HttpServletResponse response) {
		// 쿠키에서 엑세스 토큰 삭제
		Cookie accessTokenCookie = new Cookie("AccessToken", null);
		accessTokenCookie.setHttpOnly(true);
		// accessTokenCookie.setSecure(true);
		accessTokenCookie.setPath("/");
		accessTokenCookie.setMaxAge(0); // 쿠키 만료
		response.addCookie(accessTokenCookie);

		// 쿠키에서 리프레시 토큰 삭제
		Cookie refreshTokenCookie = new Cookie("RefreshToken", null);
		refreshTokenCookie.setHttpOnly(true);
		// refreshTokenCookie.setSecure(true);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge(0); // 쿠키 만료
		response.addCookie(refreshTokenCookie);

		return ResponseEntity.ok("로그아웃 성공");
	}

	@GetMapping("/info")
	public ResponseEntity<MemberDto> memberInfo() {
		//현재 인증된 사용자 정보 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		//사용자 정보 가져오기
		MemberDto memberDto = memberService.getMemberByUsername(username);




		return ResponseEntity.ok(memberDto);

	}

}
