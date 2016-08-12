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
	
	public UserInfo(String id,String pw,String name, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}
	
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
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
	
	/*
	 * 주의할점
	 * context-security.xml에서 max-sessions를 정의해도 해당 클래스에서 hashCode,equals를 정의하지 않거나 제대로 정의하지 않으면
	 * max-sessions 정책이 제대로 작동하지 않는다 
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + id.hashCode();
		result = 37 * result + pw.hashCode();
		result = 37 * result + name.hashCode();
		result = 37 * result + authorities.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		
		if(obj instanceof UserInfo == false) {
			return false;
		}
		
		UserInfo ui = (UserInfo)obj;
		
		return (this.id.equals(ui.getId())) && (this.pw.equals(ui.getPassword()));
	}
}
