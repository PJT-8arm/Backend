package com.example.be8arm.domain.recruitment.recruitment.entity;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.global.TimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static lombok.AccessLevel.PROTECTED;


@Getter
@Setter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
public class RecruitmentDto {

	private String title;

	private String content;

	private LocalDateTime recruit_date;

	private String place;

	private String partnerGender;

	private Integer partnerAge;

	private String routine;

	private LocalTime duration;


	public RecruitmentDto(Recruitment recruitment) {
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
