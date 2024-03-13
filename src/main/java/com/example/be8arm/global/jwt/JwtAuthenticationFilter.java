package com.example.be8arm.global.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends GenericFilterBean {
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
		ServletException {
		// 1. 쿠키에서 엑세스, 리프레쉬 토큰 추출
		String accessToken = jwtTokenProvider.resolveAccessToken((HttpServletRequest)request);
		String refreshToken = jwtTokenProvider.resolveRefreshToken((HttpServletRequest)request);

		// 2. validateToken으로 토큰 유효성 검사
		if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
			// 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			Authentication authentication = jwtTokenProvider.getAuthentication(accessToken, refreshToken, httpResponse);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}
		chain.doFilter(request, response);
	}

}
