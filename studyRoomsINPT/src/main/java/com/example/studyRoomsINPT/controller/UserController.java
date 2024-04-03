package com.example.studyRoomsINPT.controller;

import com.example.studyRoomsINPT.model.User;
import com.example.studyRoomsINPT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PutMapping("/{username}/password")
    public ResponseEntity<String> updatePassword(@PathVariable String username, @RequestParam String newPassword) {
        String message = userService.updatePassword(username, newPassword);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{username}")
    public ResponseEntity<String> updatePersonalData(@PathVariable String username, @RequestParam String newEmail, @RequestParam String newPassword) {
        String message = userService.updatePersonalData(username, newEmail, newPassword);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        String message = userService.deleteUserByUsername(username);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String searchTerm) {
        List<User> users = userService.searchUsers(searchTerm);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User user = userService.findUserByIdOrEmailOrUsername(id, null, null);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email")
    public ResponseEntity<User> findUserByEmail(@RequestParam String email) {
        User user = userService.findUserByIdOrEmailOrUsername(null, email, null);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username")
    public ResponseEntity<User> findUserByUsername(@RequestParam String username) {
        User user = userService.findUserByIdOrEmailOrUsername(null, null, username);
        return ResponseEntity.ok(user);
    }
}
