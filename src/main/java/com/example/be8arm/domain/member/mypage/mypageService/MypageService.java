package com.example.be8arm.domain.member.mypage.mypageService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.member.entity.Profile;
import com.example.be8arm.domain.member.member.service.MemberService;
import com.example.be8arm.domain.member.mypage.dto.ProfileDto;
import com.example.be8arm.domain.member.mypage.repository.ProfileRepository;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentListResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.service.RecruitmentService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MypageService {
	private final MemberService memberService;
	private final ProfileRepository profileRepository;
	private final RecruitmentService recruitmentService;

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

	public List<RecruitmentListResponseDto> findMyRecruitment(Member member) {
		return recruitmentService.findMyRecruitmentList(member);

	}
}
