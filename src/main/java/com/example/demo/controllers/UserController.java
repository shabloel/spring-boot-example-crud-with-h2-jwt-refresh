package com.example.demo.controllers;

import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.services.HelperService;
import com.example.demo.services.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final HelperService helperService;

    public UserController(UserService userService, HelperService helperService) {
        this.userService = userService;
        this.helperService = helperService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/users/username")
    public ResponseEntity<User> getUser(@RequestParam String username) {
        return ResponseEntity.ok().body(userService.getUser(username).get());
    }

    @PostMapping("/users/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/users/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/roles/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/roles/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/roles/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void getFreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        helperService.createAndSentTokens(request, response, userService);
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}
