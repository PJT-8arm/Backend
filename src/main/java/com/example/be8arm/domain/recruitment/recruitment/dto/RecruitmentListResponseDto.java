package com.example.be8arm.domain.recruitment.recruitment.dto;

import com.example.be8arm.domain.member.member.entity.Member;

import java.time.LocalDateTime;

public class RecruitmentListResponseDto { // 전체 글 조회 dto
    private Member member;

    private String title;

    private LocalDateTime recruit_date;

    private String partnerGender;

    private Integer partnerAge;

    private String routine;

    public RecruitmentListResponseDto(Member member, String title, LocalDateTime recruit_date, String partnerGender, Integer partnerAge, String routine) {
        this.member = member;
        this.title = title;
        this.recruit_date = recruit_date;
        this.partnerGender = partnerGender;
        this.partnerAge = partnerAge;
        this.routine = routine;
    }
}
