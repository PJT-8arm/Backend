package com.example.be8arm.domain.member.mypage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.be8arm.domain.member.member.dto.MemberDto;
import com.example.be8arm.domain.member.member.dto.SignUpDto;
import com.example.be8arm.domain.member.member.service.MemberService;
import com.example.be8arm.global.security.UserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageController {

	private MemberService memberservice;

	//todo 보류. GET 회원정보 수정 전 비밀번호 재확인 페이지 - 데이터=username
	//todo 보류. POST 회원정보 수정 전 비밀번호 재확인 페이지 -
	//Todo GET 회원정보 수정 페이지 - 데이터 member정보
	@GetMapping("/modify")
	public ResponseEntity<?> mypageDetails(@AuthenticationPrincipal UserPrincipal member) {

		// todo 예외 적용 필요 - 24.3.11
		MemberDto dto = MemberDto.toDto(memberservice.findByUsername(member.getUsername()));
		ResponseEntity<MemberDto> responseEntity = ResponseEntity.ok(dto);
		return responseEntity;
	}

	//todo POST 회원정보 수정 페이지 - 데이터 member정보
	@PostMapping("/modify")
	public ResponseEntity<SignUpDto> mypageDetails(@AuthenticationPrincipal UserPrincipal member,
		@RequestBody SignUpDto signUpDto) {
		ResponseEntity<SignUpDto> responseEntity;
		try {
			SignUpDto dto = memberservice.modifyDetails(member.getUsername(), signUpDto);
			responseEntity = ResponseEntity.ok(dto);
		} catch (Exception e) {
			responseEntity = ResponseEntity.badRequest().build();
		}
		return responseEntity;
	}
}
