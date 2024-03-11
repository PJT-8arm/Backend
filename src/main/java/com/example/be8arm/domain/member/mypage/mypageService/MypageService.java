package com.example.be8arm.domain.member.mypage.mypageService;

import org.springframework.stereotype.Service;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.member.service.MemberService;
import com.example.be8arm.domain.member.mypage.dto.ProfileDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageService {
	private final MemberService memberService;

	public ProfileDto getProfile(String username) {
		Member member = memberService.findByUsername(username);

		// profile이 null일 경우 새로 추가
		if (member.notHasProfile()) {
			member.createProfile();
		}

		return new ProfileDto(member.getProfile());
	}

}
