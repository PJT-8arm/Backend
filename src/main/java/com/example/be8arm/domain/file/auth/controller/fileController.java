package com.example.be8arm.domain.file.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.be8arm.domain.file.auth.dto.PreSignedUrlDto;
import com.example.be8arm.domain.file.auth.service.AwsAuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "File", description = "FileApi")
public class fileController {

	private final AwsAuthService awsAuthService;

	@GetMapping("/upload/url")
	public ResponseEntity<PreSignedUrlDto> getPreSignedUrl(
		@RequestParam String prefix,
		@RequestParam(required = false) String fileName
	) {
		String preSignedUrl = awsAuthService.getPreSignedUrl(prefix, fileName);

		return ResponseEntity.ok(new PreSignedUrlDto(preSignedUrl));
	}

}
