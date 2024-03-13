package com.example.be8arm.domain.member.mypage.mypageService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.member.entity.Profile;
import com.example.be8arm.domain.member.member.repository.MemberRepository;
import com.example.be8arm.domain.member.member.service.MemberService;
import com.example.be8arm.domain.member.mypage.dto.ProfileDto;
import com.example.be8arm.domain.member.mypage.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MypageService {
	private final MemberService memberService;
	private final ProfileRepository profileRepository;
	private final MemberRepository memberRepository;

	public ProfileDto getProfile(String username) {
		Member member = memberService.findByUsername(username);
		Profile profile;

		// profile이 null일 경우 새로 추가
		if (member.notHasProfile()) {
			profile = initProfile(member);
		} else {
			profile = member.getProfile();
		}

		return new ProfileDto(profile);
	}

	@Transactional
	public Profile initProfile(Member member) {
		Profile profile = Profile.builder()
			.member(member)
			.build();
		return profileRepository.save(profile);
	}

	@Transactional
	public ProfileDto modifyProfile(String username, ProfileDto profileDto) {
		Member member = memberService.findByUsername(username);
		Profile profile = member.getProfile();
		profile.modify(profileDto);
		return new ProfileDto(profile);
	}
}
