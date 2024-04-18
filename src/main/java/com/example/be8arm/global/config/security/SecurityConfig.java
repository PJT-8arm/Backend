package com.example.be8arm.global.config.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.be8arm.global.jwt.JwtAuthenticationFilter;
import com.example.be8arm.global.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Value("${custom.front.baseUrl}")
	private String webAppUrl;
	private final JwtTokenProvider jwtTokenProvider;

	// cors Configuration
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowedOrigins(List.of(webAppUrl, "http://localhost:3000"));
		config.setAllowedMethods(List.of("GET", "POST","PUT", "DELETE", "OPTIONS"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// REST API이므로 basic auth 및 csrf 보안을 사용하지 않음
			.httpBasic(
				httpbasic ->
					httpbasic.disable()
			)
			.csrf(
				AbstractHttpConfigurer::disable
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
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
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
