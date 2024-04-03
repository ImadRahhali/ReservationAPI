package com.example.studyRoomsINPT.service;

import com.example.studyRoomsINPT.model.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    //User authenticateUser(String username, String password);
    String updatePassword(String username, String newPassword);
    String updatePersonalData(String username, String newEmail, String newPassword);
    String deleteUserByUsername(String username);
    List<User> searchUsers(String searchTerm);
    User findUserByIdOrEmailOrUsername(Long id, String email, String username);
}
