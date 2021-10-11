package com.example.demo.services;

import com.example.demo.model.User;
import com.example.demo.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    UserRepo userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDetailServiceImpl(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        log.info("userName: " + userDetails.getUsername());
        log.info("authorities: " + userDetails.getAuthorities());
        log.info("password " + userDetails.getPassword());

        return userDetails;
    }

    public void createUser(UserDetails user){
        userRepository.save((User) user);
    }
}
