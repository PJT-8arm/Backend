package com.example.be8arm.domain.recruitment.application.dto;

import com.example.be8arm.domain.recruitment.application.entity.Application;

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

	public ApplicationListDto(Application application) {
		this.id = application.getId();
		this.writerId = String.valueOf(application.getWriter().getId());
		this.partnerId = application.getPartner() != null ? String.valueOf(application.getPartner().getId()) : null;
		this.isCanceled = application.isCanceled();
		this.status = application.getStatus();
	}
}
