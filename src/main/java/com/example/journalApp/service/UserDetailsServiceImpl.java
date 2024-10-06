package com.example.journalApp.service;

import com.example.journalApp.entity.User;
import com.example.journalApp.exception.UserNotFoundException;
import com.example.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findByUserName(username);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0])) // assuming roles is a Set<String>
                .build();
    }
}
