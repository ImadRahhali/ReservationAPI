package com.example.studyRoomsINPT.service;

import com.example.studyRoomsINPT.dto.UpdatePasswordRequest;
import com.example.studyRoomsINPT.dto.UpdateUserRequest;
import com.example.studyRoomsINPT.model.User;

import java.util.List;

public interface UserService {
    String updatePassword(UpdatePasswordRequest updatePasswordRequest);
    String updateUser(UpdateUserRequest updateUserRequest);
    String deleteUserByUsername(String username);
    User findUserByIdOrEmailOrUsername(Long id, String email, String username);
}
