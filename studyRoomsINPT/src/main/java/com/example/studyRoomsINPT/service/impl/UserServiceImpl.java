package com.example.studyRoomsINPT.service.impl;

import com.example.studyRoomsINPT.dto.UpdatePasswordRequest;
import com.example.studyRoomsINPT.dto.UpdateUserRequest;
import com.example.studyRoomsINPT.exception.UserNotFoundException;
import com.example.studyRoomsINPT.model.Reservation;
import com.example.studyRoomsINPT.repository.ReservationRepository;
import com.example.studyRoomsINPT.service.UserService;
import com.example.studyRoomsINPT.model.User;
import com.example.studyRoomsINPT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public String updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        User currentUser = getCurrentLoggedInUser();

        String newPassword = updatePasswordRequest.getNewPassword();
        userRepository.updateUserPassword(currentUser.getUsername(), encoder.encode(newPassword));

        return "Password updated successfully!";
    }

    private User getCurrentLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(email)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND_MESSAGE"));
    }
    @Override
    public String updateUser(UpdateUserRequest updateUserRequest) {
        User currentUser = getCurrentLoggedInUser();

        String newUsername = updateUserRequest.getNewUsername();
        String newEmail = updateUserRequest.getNewEmail();
        String newPassword = updateUserRequest.getNewPassword();

        // Check if newPassword is not null before encoding
        if (newPassword != null) {
            newPassword = encoder.encode(newPassword);
        }

        userRepository.updateUser(currentUser.getUsername(), newUsername, newEmail, newPassword);

        return "User details updated successfully!";
    }


    @Override
    public String deleteUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return "User deleted successfully.";
        } else {
            return "User not found.";
        }
    }


    @Override
    public User findUserByIdOrEmailOrUsername(Long id, String email, String username) {
        User user = null;

        if (id != null) {
            user = userRepository.findById(id).orElse(null);
        }

        if (user == null && email != null) {
            user = userRepository.findByEmail(email).orElse(null);
        }

        if (user == null && username != null) {
            user = userRepository.findByUsername(username).orElse(null);
        }

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return user;
    }


}
