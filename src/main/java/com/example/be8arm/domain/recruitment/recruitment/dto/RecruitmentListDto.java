package com.example.be8arm.domain.recruitment.recruitment.dto;

import com.example.be8arm.domain.member.member.entity.Member;

import java.time.LocalDateTime;

public class RecruitmentListDto { // 전체 글 조회 dto
    private Member member;

    private String title;

    private LocalDateTime recruit_date;
}
