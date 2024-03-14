package com.example.be8arm.domain.member.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.be8arm.domain.member.member.dto.MemberDto;
import com.example.be8arm.domain.member.member.dto.MemberModifyDto;
import com.example.be8arm.domain.member.member.dto.SignUpDto;
import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.member.exception.UserAndWriterNotMatchException;
import com.example.be8arm.domain.member.member.repository.MemberRepository;
import com.example.be8arm.global.jwt.JwtToken;
import com.example.be8arm.global.jwt.JwtTokenProvider;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

	private final MemberRepository memberRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	public JwtToken logIn(String username, String password, HttpServletResponse response) {
		// 1. username + password 를 기반으로 Authentication 객체 생성
		// 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
			password);

		// 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
		// authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		// 3. 인증 정보를 기반으로 JWT 토큰 생성
		JwtToken jwtToken = jwtTokenProvider.generateToken(authentication, response);

		return jwtToken;
	}

	@Transactional
	public MemberDto signUp(SignUpDto signUpDto) {
		if (memberRepository.existsByUsername(signUpDto.getUsername())) {
			throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
		}
		// Password 암호화
		String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
		List<String> roles = new ArrayList<>();
		roles.add("USER");  // USER 권한 부여
		return MemberDto.toDto(memberRepository.save(signUpDto.toEntity(encodedPassword, roles)));
	}

	public Member findByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> member = memberRepository.findByUsername(username);
		if (!member.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		return member.get();
	}

	public MemberDto getMemberByUsername(String username) {
		Optional<Member> optionalMember = memberRepository.findByUsername(username);
		Member member = optionalMember.orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다: " + username));
		return MemberDto.toDto(member);
	}

	@Transactional
	public SignUpDto modifyDetails(String username, MemberModifyDto memberModifyDto) {
		Member member = findByUsername(username);

		// 접속한 유저와 받아온 유저 정보 확인
		if (!memberModifyDto.getUsername().equals(member.getUsername())) {
			throw new UserAndWriterNotMatchException("올바르지 않은 사용자입니다.");
		}

		if (memberModifyDto.getPrePassword() != null && !passwordEncoder.matches(memberModifyDto.getPrePassword(),
			member.getPassword())) {
			// Todo 비밀번호 관련 exception 추가 필요 - 24.3.11
			throw new RuntimeException("기존 비밀번호가 일치하지 않습니다.");
		}

		// 비밀번호 인코딩
		if (memberModifyDto.getPostPassword() != null) {
			memberModifyDto.setPostPassword(passwordEncoder.encode(memberModifyDto.getPostPassword()));
		}

		member.modify(memberModifyDto);

		return new SignUpDto(member);
	}
<<<<<<< HEAD

	public Member findByName(String name) throws UsernameNotFoundException {
		Optional<Member> member = memberRepository.findByName(name);
		if (!member.isPresent()) {
			throw new UsernameNotFoundException(name);
		}
		return member.get();
	}
=======
>>>>>>> 530926f (fix : 충돌해결)
}
