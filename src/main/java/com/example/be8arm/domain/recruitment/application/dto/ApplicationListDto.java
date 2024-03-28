package com.example.be8arm.domain.recruitment.application.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.be8arm.domain.recruitment.application.entity.Application;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationListDto {

	private Long id;

	private String writerId;

	private String partnerId;

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

	public ApplicationListDto(Application application) {
		this.id = application.getId();
		this.writerId = String.valueOf(application.getWriter().getId());
		this.partnerId = application.getPartner() != null ? String.valueOf(application.getPartner().getId()) : null;
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
