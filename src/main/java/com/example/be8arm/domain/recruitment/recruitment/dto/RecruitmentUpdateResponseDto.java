package com.example.be8arm.domain.recruitment.recruitment.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.recruitment.recruitment.entity.Recruitment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RecruitmentUpdateResponseDto {

	private Long id;

	private Member member;

	private String title;

	private String content;

	private LocalDateTime recruit_date;

	private String place;

	private String partnerGender;

	private Integer partnerAge;

	private String routine;

	private LocalTime duration;

	public RecruitmentUpdateResponseDto(Recruitment recruitment) {
		this.id = recruitment.getId();
		this.member = recruitment.getMember();
		this.title = recruitment.getTitle();
		this.content = recruitment.getContent();
		this.recruit_date = recruitment.getRecruit_date();
		this.place = recruitment.getPlace();
		this.partnerGender = recruitment.getPartnerGender();
		this.partnerAge = recruitment.getPartnerAge();
		this.routine = recruitment.getRoutine();
		this.duration = recruitment.getDuration();
	}
}
