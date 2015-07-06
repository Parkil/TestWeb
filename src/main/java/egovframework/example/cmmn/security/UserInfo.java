package egovframework.example.cmmn.security;

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

public class UserInfo implements UserDetails {
	
	private static final long serialVersionUID = -40862319089012L;
	
	private String id;
	private String password;
	private String name;
	private Set<GrantedAuthority> authorities;
	
	public UserInfo(String id, String password, String name, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}
	
	//getter
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() { //해당 메소드는 override된 메소드이며 사용자 id를 반환하는 메소드임
		return getId();
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	//-gettter
	
	//setter
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	//-setter
	
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
	
	/*
	 * Collection으로 들어오는 권한정보를 정렬된 SortedSet에 다시 저장하여 반환
	 */
	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, " Can't pass a Numm object");
		
		SortedSet<GrantedAuthority> sortAuthorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());
		
		for(GrantedAuthority value : sortAuthorities) {
			Assert.notNull(value, "Not conatin any null elements");
			sortAuthorities.add(value);
		}
		
		return sortAuthorities;
	}
	
	/*
	 * 권한정보를 정렬하기 위한 정렬자
	 */
	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

		@Override
		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			if(g2.getAuthority() == null) {
				return -1;
			}
			
			if(g1.getAuthority() == null) {
				return -1;
			}
			
			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}
}
