package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.smart.dao.userrepository; // Import repository interface

@Configuration
@EnableWebSecurity
public class myconfig {

    private final userrepository userrepository; // Inject repository

    public myconfig(userrepository userrepository) {
        this.userrepository = userrepository;
    }

    @Bean
    public UserDetailsService getDetailsService() {
        return new userdetailservice(userrepository); // Pass the repository
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers("/", "/do_register", "/signup","/about", "/signin", "/css/**", "/js/**", "/img/**").permitAll()
             .requestMatchers("/user/**").authenticated()
                .and()
            .formLogin()
                .loginPage("/signin") // URL to display the login page
                .loginProcessingUrl("/userLogin") // URL to handle the login process
                .defaultSuccessUrl("/user/profile", true) // URL to redirect after successful login
                .permitAll();
        return http.build();
    }

}

