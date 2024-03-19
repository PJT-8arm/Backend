package com.example.be8arm.domain.recruitment.recruitment.dto;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.member.entity.MemberInfoDto;
import com.example.be8arm.domain.recruitment.recruitment.entity.Recruitment;
import com.example.be8arm.domain.recruitment.recruitment.entity.RecruitmentDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RecruitmentListDto { // 전체 글 조회 dto
    private MemberInfoDto memberInfoDto;

    private RecruitmentDto recruitmentDto;

    public RecruitmentListDto(Recruitment recruitment) {
        this.memberInfoDto = new MemberInfoDto(recruitment.getMember());
        this.recruitmentDto = new RecruitmentDto(recruitment);
    }
}
