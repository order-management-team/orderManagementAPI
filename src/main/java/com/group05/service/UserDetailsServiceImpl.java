package com.group05.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.group05.model.User;
import com.group05.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User userEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User with email " + email + " was not found."));

        return new org.springframework.security.core.userdetails.User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                true,
                true,
                true,
                true,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().getName())));
    }

}
