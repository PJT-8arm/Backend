package com.example.be8arm.global.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.example.be8arm.global.security.UserPrincipal;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
		ServletException {
		// 1. Request Header에서 JWT 토큰 추출
		String token = jwtTokenProvider.resolveToken((HttpServletRequest)request);

		// 2. validateToken으로 토큰 유효성 검사
		if (token != null && jwtTokenProvider.validateToken(token)) {
			// 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장


			HttpServletResponse httpResponse = (HttpServletResponse)response;
			Authentication authentication = jwtTokenProvider.getAuthentication(token, httpResponse);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}
		chain.doFilter(request, response);
	}

}
