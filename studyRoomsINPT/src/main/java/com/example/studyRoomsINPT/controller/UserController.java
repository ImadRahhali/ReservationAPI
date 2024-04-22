package com.example.studyRoomsINPT.controller;

import com.example.studyRoomsINPT.dto.UpdatePasswordRequest;
import com.example.studyRoomsINPT.dto.UpdateUserRequest;
import com.example.studyRoomsINPT.model.User;
import com.example.studyRoomsINPT.repository.UserRepository;
import com.example.studyRoomsINPT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;


    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @PutMapping("/update/password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        String message = userService.updatePassword(updatePasswordRequest);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update/user")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        String message = userService.updateUser(updateUserRequest);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        String message = userService.deleteUserByUsername(username);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User user = userService.findUserByIdOrEmailOrUsername(id, null, null);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email) {
        User user = userService.findUserByIdOrEmailOrUsername(null, email, null);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        User user = userService.findUserByIdOrEmailOrUsername(null, null, username);
        return ResponseEntity.ok(user);
    }
}
