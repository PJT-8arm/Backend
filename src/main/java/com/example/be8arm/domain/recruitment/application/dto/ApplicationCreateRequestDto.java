package com.example.be8arm.domain.recruitment.application.dto;

import com.example.be8arm.domain.member.member.entity.Member;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationCreateRequestDto {
	@NotNull
	private String writerId;

	@NonNull
	private String partnerId;

	private String status;

	// 작성자 정보를 가져오는 메서드
	public Member getWriter() {
		// 작성자 ID를 이용하여 작성자 정보를 가져와서 반환
		// 예시로 Member 객체를 직접 생성하여 반환하는 것으로 가정합니다.
		// 실제 프로젝트에서는 작성자 ID를 사용하여 작성자 정보를 데이터베이스에서 조회하여 반환해야 합니다.
		return new Member(writerId, "Writer Name", "Writer Image URL", "Writer Nickname", null);
	}

	// 파트너 정보를 가져오는 메서드
	public Member getPartner() {
		if (partnerId != null) {
			// 파트너 ID가 존재한다면 파트너 정보를 가져와서 반환
			// 예시로 Member 객체를 직접 생성하여 반환하는 것으로 가정합니다.
			// 실제 프로젝트에서는 파트너 ID를 사용하여 파트너 정보를 데이터베이스에서 조회하여 반환해야 합니다.
			return new Member(partnerId, "Partner Name", "Partner Image URL", "Partner Nickname", null);
		} else {
			// 파트너 ID가 존재하지 않는다면 null 반환
			return null;
		}
	}
}
