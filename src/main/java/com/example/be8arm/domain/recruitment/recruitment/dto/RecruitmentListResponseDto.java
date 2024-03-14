package com.example.be8arm.domain.recruitment.recruitment.dto;

import java.time.LocalDateTime;

import com.example.be8arm.domain.member.member.entity.Member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
public class RecruitmentListResponseDto { // 전체 글 조회 dto
	private Member member; // todo Member의 정보 중 필요한 정보만 추출 필요. member 그대로 사용 시 순환참조 발생. - 전희영 24.3.13

    private Long id;

    private String title;

	private LocalDateTime recruit_date;

	private String partnerGender;

	private Integer partnerAge;

	private String routine;

    public RecruitmentListResponseDto(Member member, Long id, String title, LocalDateTime recruit_date, String partnerGender, Integer partnerAge, String routine) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.recruit_date = recruit_date;
        this.partnerGender = partnerGender;
        this.partnerAge = partnerAge;
        this.routine = routine;
    }
}
