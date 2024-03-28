package com.example.be8arm.global.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	@Value("${custom.front.baseUrl}")
	private String webAppUrl;

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		// 특정 도메인을 허용하고 모든 HTTP 메서드를 허용합니다.
		config.addAllowedOrigin("https://app.genj.me"); // 리액트 애플리케이션의 주소로 변경
		config.addAllowedOrigin("http://localhost:3000");
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.setAllowCredentials(true);

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}

