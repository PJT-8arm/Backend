package com.example.be8arm.domain.member.mypage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.be8arm.domain.member.member.dto.MemberDto;
import com.example.be8arm.domain.member.member.dto.MemberModifyDto;
import com.example.be8arm.domain.member.member.dto.SignUpDto;
import com.example.be8arm.domain.member.member.service.MemberService;
import com.example.be8arm.domain.member.mypage.dto.ProfileDto;
import com.example.be8arm.domain.member.mypage.mypageService.MypageService;
import com.example.be8arm.global.security.UserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageController {

	private final MemberService memberService;
	private final MypageService mypageService;

	//todo 보류. GET 회원정보 수정 전 비밀번호 재확인 페이지 - 데이터=username
	//todo 보류. POST 회원정보 수정 전 비밀번호 재확인 페이지
	@GetMapping("/modify")
	public ResponseEntity<?> mypageDetails(@AuthenticationPrincipal UserPrincipal member) {

		// todo 예외 적용 필요 - 24.3.11
		MemberDto dto = MemberDto.toDto(memberService.findByUsername(member.getUsername()));
		ResponseEntity<MemberDto> responseEntity = ResponseEntity.ok(dto);
		return responseEntity;
	}

	@PostMapping("/modify")
	public ResponseEntity<SignUpDto> mypageModifyDetails(@AuthenticationPrincipal UserPrincipal member,
		@RequestBody MemberModifyDto memberModifyDto) {
		ResponseEntity<SignUpDto> responseEntity;
		try {
			SignUpDto dto = memberService.modifyDetails(member.getUsername(), memberModifyDto);
			responseEntity = ResponseEntity.ok(dto);
		} catch (Exception e) {
			responseEntity = ResponseEntity.badRequest().build();
		}
		return responseEntity;
	}

	// todo GET 프로필 페이지 프로필 데이터 - 24.03.11
	@GetMapping("/profile")
	public ResponseEntity<ProfileDto> mypageProfile(@AuthenticationPrincipal UserPrincipal member) {
		ResponseEntity<ProfileDto> responseEntity;

		try {
			ProfileDto dto = mypageService.getProfile(member.getUsername());
			responseEntity = ResponseEntity.ok(dto);
		} catch (Exception e) {
			responseEntity = ResponseEntity.badRequest().build();
		}
		return responseEntity;
	}

	// todo POST 프로필 수정 프로필 데이터 - 24.03.11
	@PostMapping("/profile")
	public ResponseEntity<ProfileDto> mypageModifyProfile(@AuthenticationPrincipal UserPrincipal member,
		@RequestBody ProfileDto profileDto) {
		ResponseEntity<ProfileDto> responseEntity;
		try {
			ProfileDto dto = mypageService.modifyProfile(member.getUsername(), profileDto);
			responseEntity = ResponseEntity.ok(dto);
		} catch (Exception e) {
			throw e;
			// responseEntity = ResponseEntity.badRequest().build();
		}
		return responseEntity;
	}

	// todo GET 내가 작성한 글 조회 모집글 데이터 - 24.03.11
	// todo GET 내 약속 조회 모집글 데이터 - 24.03.11
}
