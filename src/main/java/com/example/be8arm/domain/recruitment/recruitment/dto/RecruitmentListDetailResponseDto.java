package com.example.be8arm.domain.recruitment.recruitment.dto;

import com.example.be8arm.domain.member.member.entity.MemberInfoDto;
import com.example.be8arm.domain.member.member.entity.ProfileDto;
import com.example.be8arm.domain.recruitment.recruitment.entity.Recruitment;
import com.example.be8arm.domain.recruitment.recruitment.entity.RecruitmentDto;

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
public class RecruitmentListDetailResponseDto {

	private MemberInfoDto memberInfoDto;

	private RecruitmentDto recruitmentDto;

	private ProfileDto profileDto;

	public RecruitmentListDetailResponseDto(Recruitment recruitment) {
		this.memberInfoDto = new MemberInfoDto(recruitment.getMember());
		this.recruitmentDto = new RecruitmentDto(recruitment);

		// recruitment.getMember().getProfile()이 null인 경우를 체크하여 처리
		if (recruitment.getMember().getProfile() != null) {
			this.profileDto = new ProfileDto(recruitment.getMember().getProfile());
		} else {
			this.profileDto = null; // 또는 적절한 기본 값을 설정
		}
	}
}
