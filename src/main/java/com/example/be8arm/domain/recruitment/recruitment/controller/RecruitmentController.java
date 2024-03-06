package com.example.be8arm.domain.recruitment.recruitment.controller;

import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentCreateRequestDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentCreateResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentListDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentUpdateResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.entity.Recruitment;
import com.example.be8arm.domain.recruitment.recruitment.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitments")
@Slf4j
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @PostMapping("/write") // 작성
    public ResponseEntity<RecruitmentCreateResponseDto> recruitmentAdd(
            @AuthenticationPrincipal UserPrincipal user,
            @ModelAttribute RecruitmentCreateRequestDto recruitmentCreateRequestDto){

        RecruitmentCreateResponseDto recruitmentCreateResponseDto = recruitmentService.addRecruitment(user.getMember(), recruitmentCreateRequestDto);

        return ResponseEntity.ok(recruitmentCreateResponseDto);
    }

    @PutMapping("/update/{id}") // 수정
    public ResponseEntity<RecruitmentUpdateResponseDto> recruitmentUpdate(
        @PathVariable Long id,
        @AuthenticationPrincipal UserPrincipal user,
        @ModelAttribute RecruitmentCreateRequestDto recruitmentUpdateRequestDto) {

        RecruitmentUpdateResponseDto recruitmentUpdateResponseDto = recruitmentService.updateRecruitment(id, user.getMember(), recruitmentUpdateRequestDto);

        return ResponseEntity.ok(recruitmentUpdateResponseDto);
    }

}
