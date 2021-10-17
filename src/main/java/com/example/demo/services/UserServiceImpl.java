package com.example.demo.services;

import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.repositories.RoleRepo;
import com.example.demo.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//makes sure dat is saved into db without calling the repo, see method addRoleToUSer()
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service @Slf4j @Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to database", user.getUserName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role {} to database", role.getRoleName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding new role {} to user {}", roleName, username);
        Optional<User> user = userRepo.findByUserName(username);
        Role role = roleRepo.findByRoleName(roleName);
        if(user.isPresent() && role != null) {
            user.get().getRoles().add(role);
        }else{
            throw new RuntimeException("Role or user does not exist");
        }
    }

    @Override
    public Optional<User> getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUserName(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Finding all users");
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        user.getRoles().forEach(a -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(a.getRoleName())));
        return new org.springframework.security.core.userdetails
                .User(user.getUserName(), user.getPassword(), simpleGrantedAuthorities);
    }
}
