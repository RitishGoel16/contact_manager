package com.smart.config;

import java.util.Collection;
import java.util.List;

import com.smart.entities.user;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class customuserdetail implements UserDetails {

	private user user;

	public customuserdetail(user user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority SimpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());
		return List.of(SimpleGrantedAuthority);

	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {

		return user.getEmail();
	}

}
