package com.example.be8arm.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.be8arm.global.jwt.JwtAuthenticationFilter;
import com.example.be8arm.global.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// REST API이므로 basic auth 및 csrf 보안을 사용하지 않음
			.httpBasic(
				httpbasic ->
					httpbasic.disable()
			)
			.csrf(
				csrf ->
					csrf.disable()
			)
			// JWT를 사용하기 때문에 세션을 사용하지 않음
			.sessionManagement(
				s ->
					s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.headers(
				headers ->
					headers.frameOptions(
						frameOptions ->
							frameOptions.sameOrigin()
					)
			)
			.authorizeHttpRequests(
				auth ->
					auth
						.requestMatchers("/**").permitAll()  // 해당 API에 대해서는 모든 요청을 허가
						.requestMatchers("/members/test").hasRole("USER") // USER 권한이 있어야 요청할 수 있음
						.anyRequest().authenticated()
			)
			// JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// BCrypt Encoder 사용
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
