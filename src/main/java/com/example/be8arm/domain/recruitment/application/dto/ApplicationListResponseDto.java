package com.example.be8arm.domain.recruitment.application.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.be8arm.domain.member.member.entity.MemberInfoDto;
import com.example.be8arm.domain.recruitment.application.entity.Application;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.example.be8arm.domain.member.member.entity.MemberInfoDto;
import com.example.be8arm.domain.recruitment.application.entity.Application;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationListResponseDto {

	private MemberInfoDto writerInfoDto;

	private MemberInfoDto partnerInfoDto;

	private boolean isCanceled;

	private String status;

	private String title;

	private String content;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime recruit_date;

	private String place;

	private String partnerGender;

	private Integer partnerAge;

	private String routine;

	private LocalTime duration;

	public ApplicationListResponseDto(Application application) {
		this.writerInfoDto = new MemberInfoDto(application.getWriter());
		this.partnerInfoDto = application.getPartner() != null ? new MemberInfoDto(application.getPartner()) : null;
		this.isCanceled = application.isCanceled();
		this.status = application.getStatus();
		this.title = application.getTitle();
		this.content = application.getContent();
		this.recruit_date = application.getRecruit_date();
		this.place = application.getPlace();
		this.partnerGender = application.getPartnerGender();
		this.partnerAge = application.getPartnerAge();
		this.routine = application.getRoutine();
		this.duration = application.getDuration();
	}
}
