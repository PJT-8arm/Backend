package com.example.be8arm.global.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.member.entity.Profile;
import com.example.be8arm.domain.member.member.enums.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
	private Member member;
	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(String username, String name, String imgUrl, String nickname, Profile profile) {
		this.member = new Member(username, name, imgUrl, nickname, profile);
		this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	public static UserPrincipal create(Member member) {
		List<GrantedAuthority> authorities = Collections.
			singletonList(
				new SimpleGrantedAuthority(
					"admin".equals(member.getName()) ?
						MemberRole.ADMIN.getValue() : MemberRole.USER.getValue()
				)
			);

		return UserPrincipal.builder()
			.member(member)
			.authorities(authorities)
			.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
