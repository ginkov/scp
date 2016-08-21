package com.jiayun.scp.dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jiayun.scp.model.Role;
import com.jiayun.scp.model.Staff;

@Service
public class JiayunUserDetailsService implements UserDetailsService{

	@Autowired
	private DaoService<Staff> ss;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//		System.out.println("loadUserByUsername, name="+name);
		Staff s = ss.getByName(name);
		boolean enabled = true;
		boolean accountNonExpired = true;
	    boolean credentialsNonExpired = true;
	    boolean accountNonLocked = true;

		return new User(
				s.getName(),
				s.getPass_md5(),
				enabled,
				accountNonExpired,
				credentialsNonExpired,
				accountNonLocked,
				getAuthorities(s)
		);

	}

	private Collection<? extends GrantedAuthority> getAuthorities(Staff s){
		Set<GrantedAuthority> gas = new HashSet<>();
		for(Role r: s.getRoles()) {
			gas.add(new SimpleGrantedAuthority(r.getRole()));
		}
		return gas;
	}
	
//	@Bean
//	public JiayunUserDetailsService jiayunUserDetailService() {
//		return new JiayunUserDetailsService();
//	}
}

