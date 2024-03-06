package com.example.be8arm.domain.recruitment.recruitment.dto;

import com.example.be8arm.domain.member.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RecruitmentListDto { // 전체 글 조회 dto
    private Member member;

    private String title;

    private LocalDateTime recruit_date;
}
