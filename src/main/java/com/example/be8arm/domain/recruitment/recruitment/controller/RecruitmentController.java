package com.example.be8arm.domain.recruitment.recruitment.controller;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentCreateRequestDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentCreateResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentListDetailResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentListResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentUpdateResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitments")
@Slf4j
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @PostMapping("/write") // 작성
    public ResponseEntity<RecruitmentCreateResponseDto> recruitmentAdd(
            @AuthenticationPrincipal Member user,
            @RequestBody RecruitmentCreateRequestDto recruitmentCreateRequestDto){

        RecruitmentCreateResponseDto recruitmentCreateResponseDto = recruitmentService.addRecruitment(user, recruitmentCreateRequestDto);

        return ResponseEntity.ok(recruitmentCreateResponseDto);
    }

    @GetMapping("/list") // 목록
    public ResponseEntity<List<RecruitmentListResponseDto>> recruitmentList(){
        List<RecruitmentListResponseDto> recruitmentList = recruitmentService.findRecruitmentList();
        return ResponseEntity.ok(recruitmentList);
    }

    @GetMapping("/list{id}") // 글 상세 보기
    public ResponseEntity<RecruitmentListDetailResponseDto> recruitmentDetails(@PathVariable("id") Long id){
        RecruitmentListDetailResponseDto recruitmentDetails = recruitmentService.findRecruitment(id);
        return ResponseEntity.ok(recruitmentDetails);
    }

    @PutMapping("/update/{id}") // 수정
    public ResponseEntity<RecruitmentUpdateResponseDto> recruitmentUpdate(
        @PathVariable Long id,
        @AuthenticationPrincipal Member user,
        @RequestBody RecruitmentCreateRequestDto recruitmentUpdateRequestDto) {

        RecruitmentUpdateResponseDto recruitmentUpdateResponseDto = recruitmentService.updateRecruitment(id, user, recruitmentUpdateRequestDto);

        return ResponseEntity.ok(recruitmentUpdateResponseDto);
    }
}
