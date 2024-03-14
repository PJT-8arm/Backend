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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.member.repository.MemberRepository;
import com.example.be8arm.global.security.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	private final Key key;
	private final MemberRepository memberRepository;  // MemberRepository 주입

	// AccessToken 만료 시간
	private static final long ACCESS_TOKEN_EXPIRATION = 86400000; // 1 day

	// RefreshToken 만료 시간
	private static final long REFRESH_TOKEN_EXPIRATION = 172800000; // 2 days

	// AccessToken 재발급 시간
	private static final long ACCESS_TOKEN_RENEWAL_THRESHOLD = 60000; // 1 minute

	// application.yml에서 secret 값 가져와서 key에 저장
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, MemberRepository memberRepository) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.memberRepository = memberRepository;
	}

	// Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
	public JwtToken generateToken(Authentication authentication, HttpServletResponse response) {

		// 권한 가져오기
		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
		Member member = userPrincipal.getMember();

		// Access Token 생성
		Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRATION);
		String accessToken = Jwts.builder()
			.setSubject(authentication.getName())
			.claim("auth", authorities)
			.claim("username", member.getUsername())
			.claim("nickname", member.getNickname())
			.claim("name", member.getName())
			.claim("imgUrl", member.getImgUrl())
			.setExpiration(accessTokenExpiresIn)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();

		// Refresh Token 생성
		Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRATION);
		String refreshToken = Jwts.builder()
			.setExpiration(refreshTokenExpiresIn)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();

		// 쿠키에 토큰 추가
		addTokenToCookie(accessToken, refreshToken, response);

		return JwtToken.builder()
			.grantType("Bearer")
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	// Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
	public Authentication getAuthentication(String accessToken, String refreshToken, HttpServletResponse response) {
		// Jwt 토큰 복호화
		Claims claims = parseClaims(accessToken);

		if (claims.get("auth") == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}

		// Claims 객체가 null이면 예외 처리
		if (claims == null) {
			throw new RuntimeException("토큰이 유효하지 않습니다.");
		}

		// 클레임에서 권한 정보 가져오기
		Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());

		// username으로 Member 객체 불러오기
		String username = claims.getSubject();
		Member member = memberRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + username));

		// AccessToken이 재발급되어야 하는지 확인
		Date expiration = claims.getExpiration();
		long now = System.currentTimeMillis();
		long timeUntilExpiration = expiration.getTime() - now;

		if (timeUntilExpiration < ACCESS_TOKEN_RENEWAL_THRESHOLD) {
			// AccessToken이 재발급되어야 한다면 새로운 AccessToken을 생성
			JwtToken renewedToken = generateRenewedToken(claims, response);
			addTokenToCookie(renewedToken.getAccessToken(), renewedToken.getRefreshToken(), response);
			return getAuthentication(renewedToken.getAccessToken(), renewedToken.getRefreshToken(), response);
		}

		// UserDetails 객체를 만들어서 Authentication return
		UserDetails principal = new UserPrincipal(member, authorities);
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	private JwtToken generateRenewedToken(Claims claims, HttpServletResponse response) {
		String username = claims.getSubject();
		Member member = memberRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + username));

		String authorities = claims.get("auth", String.class);

		Date accessTokenExpiresIn = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION);
		String accessToken = Jwts.builder()
			.setSubject(username)
			.claim("auth", authorities)
			.claim("username", member.getUsername())
			.claim("nickname", member.getNickname())
			.claim("name", member.getName())
			.claim("imgUrl", member.getImgUrl())
			.setExpiration(accessTokenExpiresIn)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();

		Date refreshTokenExpiresIn = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION);
		String refreshToken = Jwts.builder()
			.setExpiration(refreshTokenExpiresIn)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();


		return JwtToken.builder()
			.grantType("Bearer")
			.accessToken(accessToken)
			.refreshToken(null)
			.build();
	}

	private void addTokenToCookie(String accessToken, String refreshToken, HttpServletResponse response) {
		// 엑세스 토큰 쿠키 추가
		Cookie accessTokenCookie = new Cookie("AccessToken", accessToken);
		accessTokenCookie.setHttpOnly(true);
		accessTokenCookie.setSecure(true);
		accessTokenCookie.setPath("/");
		accessTokenCookie.setMaxAge(60 * 60 * 24); // 1일
		response.addCookie(accessTokenCookie);

		// 리프레시 토큰 쿠키 추가
		Cookie refreshTokenCookie = new Cookie("RefreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge(60 * 60 * 24 * 2); // 2일
		response.addCookie(refreshTokenCookie);
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

	// accessToken에서 클레임 가져오기
	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody(); // 클레임 내용
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	public String resolveAccessToken(HttpServletRequest request) {
		// 쿠키에서 엑세스 토큰 추출
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("AccessToken".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public String resolveRefreshToken(HttpServletRequest request) {
		// 쿠키에서 리프레시 토큰 추출
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("RefreshToken".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

}
