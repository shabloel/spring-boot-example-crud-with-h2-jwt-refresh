package com.example.demo.services;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    Optional<User> getUser(String username);
    List<User> getUsers();
}
