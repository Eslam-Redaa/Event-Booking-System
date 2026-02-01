package com.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.demo.Entities.UserEntity;
import com.demo.Exceptions.ResourceNotFoundException;
import com.demo.Repositories.UserRepository;

@Component
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository urepo;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = urepo.findByEmail(username)
				.orElseThrow( () -> new ResourceNotFoundException("User not Found : " + username));
		
		return new MyUserDetails(user);
	}

}
