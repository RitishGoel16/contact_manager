package com.smart.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.smart.dao.userrepository;
import com.smart.entities.user;
public class userdetailservice implements UserDetailsService{
private userrepository userrepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		user user = userrepository.getuserbyusername(username);
		if (user==null) {
			throw new UsernameNotFoundException("could not found user");
		}
		customuserdetail customuserdetails=new customuserdetail(user);
		
		return customuserdetails;
	}

}
