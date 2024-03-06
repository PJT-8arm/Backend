package com.example.be8arm.global.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	private final Key key;

	// application.yml에서 secret 값 가져와서 key에 저장
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	// Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
	public JwtToken generateToken(Authentication authentication) {
		// 권한 가져오기
		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		long now = (new Date()).getTime();

		// Access Token 생성
		Date accessTokenExpiresIn = new Date(now + 86400000);
		String accessToken = Jwts.builder()
			.setSubject(authentication.getName())
			.claim("auth", authorities)
			.setExpiration(accessTokenExpiresIn)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();

		// Refresh Token 생성
		String refreshToken = Jwts.builder()
			.setExpiration(new Date(now + 86400000))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();

		return JwtToken.builder()
			.grantType("Bearer")
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	// Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
	public Authentication getAuthentication(String accessToken) {
		// Jwt 토큰 복호화
		Claims claims = parseClaims(accessToken);

		if (claims.get("auth") == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}

		// 클레임에서 권한 정보 가져오기
		Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());

		// UserDetails 객체를 만들어서 Authentication return
		// UserDetails: interface, User: UserDetails를 구현한 class
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	// 토큰 정보를 검증하는 메서드
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.", e);
		}
		return false;
	}

	// accessToken
	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	public String resolveToken(HttpServletRequest request) {
		// Authorization 헤더에서 토큰 추출
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // "Bearer " 다음의 토큰 부분만 반환
		}
		return null;
	}

	public void invalidateToken(HttpServletRequest request) {
		// 클라이언트에게 전달된 토큰을 무효화
		String token = resolveToken(request);
		// 토큰을 무효화하는 추가적인 작업 수행 (예: 블랙리스트에 추가)

		// 블랙리스트에 추가한 토큰은 검증에서 실패하도록 설정
		// (JwtAuthenticationFilter의 doFilter 메서드에서 검증 시 블랙리스트 체크 추가 필요)
	}

}
