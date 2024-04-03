package com.example.studyRoomsINPT.service.impl;

import com.example.studyRoomsINPT.exception.UserNotFoundException;
import com.example.studyRoomsINPT.model.Reservation;
import com.example.studyRoomsINPT.repository.ReservationRepository;
import com.example.studyRoomsINPT.service.UserService;
import com.example.studyRoomsINPT.model.User;
import com.example.studyRoomsINPT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
        // Add any validation or additional logic here before saving
        return userRepository.save(user);
    }

    @Override
    public String updatePassword(String username, String newPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return "Password updated successfully.";
        } else {
            return "User not found.";
        }
    }


    @Override
    public String updatePersonalData(String username, String newEmail, String newPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(newEmail);
            user.setPassword(newPassword);
            userRepository.save(user);
            return "Personal data updated successfully.";
        } else {
            return "User not found.";
        }
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
    public List<User> searchUsers(String searchTerm) {
        // Implement search logic based on the searchTerm
        return userRepository.findAll(); // For demonstration, returning all users
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
