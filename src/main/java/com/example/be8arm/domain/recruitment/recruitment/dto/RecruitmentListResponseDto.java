package com.example.be8arm.domain.recruitment.recruitment.dto;

import java.time.LocalDateTime;

import com.example.be8arm.domain.member.member.entity.Member;

import com.example.be8arm.domain.member.member.entity.MemberInfoDto;
import com.example.be8arm.domain.recruitment.recruitment.entity.Recruitment;
import com.example.be8arm.domain.recruitment.recruitment.entity.RecruitmentDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RecruitmentListResponseDto { // 전체 글 조회 dto
	private MemberInfoDto memberInfoDto;

    private RecruitmentDto recruitmentDto;

    public RecruitmentListResponseDto(Recruitment recruitment) {
        this.memberInfoDto = new MemberInfoDto(recruitment.getMember());
        this.recruitmentDto = new RecruitmentDto(recruitment);
    }
}
