package com.example.be8arm.domain.recruitment.application.controller;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.recruitment.application.dto.ApplicationCreateRequestDto;
import com.example.be8arm.domain.recruitment.application.dto.ApplicationCreateResponseDto;
import com.example.be8arm.domain.recruitment.application.dto.ApplicationListDto;
import com.example.be8arm.domain.recruitment.application.dto.ApplicationListResponseDto;
import com.example.be8arm.domain.recruitment.application.service.ApplicationService;
import com.example.be8arm.global.security.UserPrincipal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

	private final ApplicationService applicationService;

	@PostMapping("/create")
	public ResponseEntity<ApplicationCreateResponseDto> createApplication(
		@AuthenticationPrincipal UserPrincipal user, // 인증된 회원 정보를 받아옴
		@RequestBody ApplicationCreateRequestDto requestDto) {
		ApplicationCreateResponseDto responseDto = applicationService.createApplication(user.getMember(), user.getUsername(), requestDto);
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/list") // 약속 목록
	public ResponseEntity<List<ApplicationListDto>> getAllApplications() {
		List<ApplicationListDto> applicationList = applicationService.findApplicationList();
		return ResponseEntity.ok(applicationList);
	}

	@GetMapping("/list/{id}") // 약속 상세 보기
	public ResponseEntity<ApplicationListResponseDto> applicationDetails(@PathVariable("id") Long id){
		ApplicationListResponseDto applicationDetails = applicationService.findApplication(id);
		return ResponseEntity.ok(applicationDetails);
	}
}
