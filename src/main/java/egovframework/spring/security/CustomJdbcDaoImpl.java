package egovframework.spring.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

/*
 * Spring security 권한정보를 DB에서 가져오는 클래스
 */
public class CustomJdbcDaoImpl extends JdbcDaoImpl {
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetails> users = loadUsersByUsername(username);
		
		if(users.size() == 0) {
			logger.debug("Query returned no results for user '"+username+"'");
			
			throw new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.notFound", new Object[] {username}, "Username{0} not found"));
		}
		
		UserInfo user = (UserInfo)users.get(0);
		
		Set<GrantedAuthority> dbAuthSet = new HashSet<GrantedAuthority>();
		
		if(super.getEnableAuthorities()) {
			dbAuthSet.addAll(loadUserAuthorities(user.getId()));
		}
		
		if(super.getEnableGroups()) {
			dbAuthSet.addAll(loadGroupAuthorities(user.getId()));
		}
		
		List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthSet);
		user.setAuthorities(dbAuths);
		
		if(dbAuths.size() == 0) {
			logger.debug("User '"+username+"' has no authorities and will be treated as 'not found");
			
			throw new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.noAuthority", new Object[] {username}, "Username{0} has no GrantedAuthority"));
		}
		
		return user;
	}
	
	@Override
	protected List<UserDetails> loadUsersByUsername(String username) {
		return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] {username}, new RowMapper<UserDetails>() {

			@Override
			public UserDetails mapRow(ResultSet rs, int rownum)throws SQLException {
				String username = rs.getString(1);
				String password = rs.getString(2);
				String name		= rs.getString(3);
				return new UserInfo(username, password, name, AuthorityUtils.NO_AUTHORITIES);
			}
		});
	}
	
	@Override
	protected List<GrantedAuthority> loadUserAuthorities(String username) {
		return getJdbcTemplate().query(getAuthoritiesByUsernameQuery(), new String[] {username}, new RowMapper<GrantedAuthority>() {

			@Override
			public GrantedAuthority mapRow(ResultSet rs, int rownum)throws SQLException {
				String roleName = getRolePrefix() + rs.getString(1);
				return new SimpleGrantedAuthority(roleName);
			}
		});
	}
	
	@Override
	protected List<GrantedAuthority> loadGroupAuthorities(String username) {
		return super.loadGroupAuthorities(username);
	}
}
