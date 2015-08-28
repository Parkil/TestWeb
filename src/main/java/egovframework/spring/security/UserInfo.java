package egovframework.spring.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/*
 * Spring Security 사용자 계정 관리 클래스
 */
public class UserInfo implements UserDetails {

	private static final long serialVersionUID = -6285503781877874478L;
	
	private String id;
	private String pw;
	private String name;
	private Set<GrantedAuthority> authorities;
	
	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setPassword(String pw) {
		this.pw = pw;
	}
	
	@Override
	public String getPassword() {
		return pw;
	}
	
	public void setUsername(String name) {
		this.name = name;
	}
	
	@Override
	public String getUsername() {
		return name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	//권한정렬 관련 메소드
	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, "Granted Authorities must not null");
		
		SortedSet<GrantedAuthority> sortedAuth = new TreeSet<GrantedAuthority>(new AuthorityComparator());
		
		for(GrantedAuthority ga : authorities) {
			sortedAuth.add(ga);
		}
		
		return sortedAuth;
	}
	
	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		
		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

		@Override
		public int compare(GrantedAuthority o1, GrantedAuthority o2) {
			if(o2.getAuthority() ==  null) {
				return -1;
			}
			
			if(o1.getAuthority() == null) {
				return 1;
			}
			
			return o1.getAuthority().compareTo(o2.getAuthority());
		}
	}
}
