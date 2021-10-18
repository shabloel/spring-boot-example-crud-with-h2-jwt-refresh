package com.example.demo.services;

import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    Optional<User> getUser(String username);

    List<User> getUsers();
}
