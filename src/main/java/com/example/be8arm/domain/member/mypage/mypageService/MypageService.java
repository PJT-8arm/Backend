package com.example.be8arm.domain.member.mypage.mypageService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.member.entity.Profile;
import com.example.be8arm.domain.member.member.service.MemberService;
import com.example.be8arm.domain.member.mypage.dto.ProfileDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
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

	@Transactional
	public ProfileDto modifyProfile(String username, ProfileDto profileDto) {
		Member member = memberService.findByUsername(username);
		Profile profile = member.getProfile();
		profile.modify(profileDto);
		return new ProfileDto(profile);
	}
}
