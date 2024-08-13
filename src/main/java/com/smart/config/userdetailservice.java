package com.smart.config;

/*
 * package com.smart.config;
 * 
 * import org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.core.userdetails.UsernameNotFoundException;
 * 
 * import com.smart.dao.userrepository; import com.smart.entities.user;
 * 
 * public class userdetailservice implements UserDetailsService {
 * 
 * private final userrepository userrepository;
 * 
 * public userdetailservice(userrepository userrepository) { this.userrepository
 * = userrepository; }
 * 
 * @Override public UserDetails loadUserByUsername(String username) throws
 * UsernameNotFoundException { user user = userrepository.findByEmail(username);
 * // Use the username parameter if (user == null) { throw new
 * UsernameNotFoundException("User not found"); } return new
 * customuserdetail(user); } }
 */


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.entities.user;

import java.util.Collection;
import java.util.Collections;

public class userdetailservice implements UserDetailsService {

    private final com.smart.dao.userrepository userrepository;

    public userdetailservice(com.smart.dao.userrepository userrepository) {
        this.userrepository = userrepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        user user = userrepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), 
                user.getPassword(), 
                user.isEnabled(),  // Check if the account is enabled
                true, 
                true, 
                true, 
                getAuthorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(user user) {
        // Implement authority fetching if needed
        return Collections.emptyList();
    }
}