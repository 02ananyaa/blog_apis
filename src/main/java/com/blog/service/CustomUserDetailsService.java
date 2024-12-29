package com.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.repo.UserRepo;

//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//	// Inject your User repository or data source here
//	@Autowired
//	private final UserRepo userRepository;
//
////	public CustomUserDetailsService(UserRepo userRepository) {
////		this.userRepository = userRepository;
////	}
//
//	@Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
////	 fetching user from database
//		User user = userRepository.findByEmail(email)
//		            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//
//      
//
//        // Return UserDetails object. In this case, using Spring Security's User class.
//        return org.springframework.security.core.userdetails.User.builder()
//                   .username(user.getUsername())
//                   .password(user.getPassword()) // Password should be encoded
//                   .roles(null) // Set roles (if applicable)
//                   .build();
////        return user;
//    }
//}
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      UserDetails user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return user;
    }
}
//
