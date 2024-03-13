package com.example.be8arm.global.initNotProd;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.be8arm.domain.member.member.dto.SignUpDto;
import com.example.be8arm.domain.member.member.entity.Gender;
import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.member.service.MemberService;
import com.example.be8arm.domain.member.mypage.mypageService.MypageService;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentCreateRequestDto;
import com.example.be8arm.domain.recruitment.recruitment.service.RecruitmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Profile("!prod")
@RequiredArgsConstructor
@Slf4j
@Configuration
public class NotProde {
	private final MemberService memberService;
	private final RecruitmentService recruitmentService;
	private final MypageService mypageService;

	@Bean
	public ApplicationRunner devInit() {
		return new ApplicationRunner() {

			@Override
			public void run(ApplicationArguments args) throws Exception {

				for (int i = 0; i < 4; i++) {
					SignUpDto signUpDto = SignUpDto.builder()
						.username("user" + i)
						.password("1234")
						.checkPassword("1234")
						.nickname("hihi")
						.name("user" + i)
						.build();
					try {
						memberService.signUp(signUpDto);
					} catch (Exception e) {
						continue;
					}
				}
				Member user0 = memberService.findByUsername("user0");
				Member user1 = memberService.findByUsername("user1");
				Member user2 = memberService.findByUsername("user2");
				for (int i = 0; i < 10; i++) {
					RecruitmentCreateRequestDto RRqdto = RecruitmentCreateRequestDto.builder()
						.content("content" + i)
						.duration(LocalTime.now())
						.title("title" + i)
						.place("place" + i)
						.partnerAge(30)
						.partnerGender(Gender.Female.getValue())
						.recruit_date(LocalDateTime.now().plus(Period.ofDays(i)))
						.routine("")
						.build();
					recruitmentService.addRecruitment(user1, RRqdto);
				}
			}
		};
	}
}
