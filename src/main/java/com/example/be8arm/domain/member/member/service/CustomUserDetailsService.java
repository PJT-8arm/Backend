package com.example.be8arm.domain.member.member.service;

import com.example.be8arm.global.security.UserPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
		return UserPrincipal.create(member);
	}

	// 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
	private UserDetails createUserDetails(Member member) {
		return User.builder()
			.username(member.getUsername())
			.password(passwordEncoder.encode(member.getPassword()))
			.roles(member.getRoles().toArray(new String[0]))
			.build();
	}

}
