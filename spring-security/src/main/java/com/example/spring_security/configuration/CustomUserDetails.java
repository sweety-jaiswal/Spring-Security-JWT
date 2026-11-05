package com.example.spring_security.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.spring_security.model.Person;

public class CustomUserDetails implements UserDetails{
	

	String username;
	String password;
	List<GrantedAuthority> authorities;

	public CustomUserDetails(Person person) {
		// TODO Auto-generated constructor stub
		
		this.username = person.getUsername();
		this.password = person.getPassword();
		
		String[] roles = person.getRoles().split(",");
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for(String role: roles) {
			SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + role);
			grantedAuthorities.add(simpleGrantedAuthority);
		}
		this.authorities = grantedAuthorities;
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public @Nullable String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	
}
