//
// import java.util.Collection;
//
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
//
// public class CustomUserDetails implements UserDetails {
//
// 	private final String username;
// 	private final String password;
// 	private final Collection<? extends GrantedAuthority> authorities;
// 	private final String nickname;
// 	private final String name;
// 	private final String imgUrl;
//
// 	public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
// 		String nickname, String name, String imgUrl) {
// 		this.username = username;
// 		this.password = password;
// 		this.authorities = authorities;
// 		this.nickname = nickname;
// 		this.name = name;
// 		this.imgUrl = imgUrl;
// 	}
//
// 	@Override
// 	public boolean isAccountNonExpired() {
// 		return true;
// 	}
//
// 	@Override
// 	public boolean isAccountNonLocked() {
// 		return true;
// 	}
//
// 	@Override
// 	public boolean isCredentialsNonExpired() {
// 		return true;
// 	}
//
// 	@Override
// 	public boolean isEnabled() {
// 		return true;
// 	}
//
// 	@Override
// 	public Collection<? extends GrantedAuthority> getAuthorities() {
// 		return authorities;
// 	}
//
// 	@Override
// 	public String getPassword() {
// 		return password;
// 	}
//
// 	@Override
// 	public String getUsername() {
// 		return username;
// 	}
//
// 	public String getNickname() {
// 		return nickname;
// 	}
//
// 	public String getName() {
// 		return name;
// 	}
//
// 	public String getImgUrl() {
// 		return imgUrl;
// 	}
// }
//
