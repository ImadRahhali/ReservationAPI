package com.example.studyRoomsINPT.repository;

import com.example.studyRoomsINPT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String username);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.username = :newUsername WHERE u.id = :id")
    void updateUsername(@Param("id") Long id, @Param("newUsername") String newUsername);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :id")
    void updatePassword(@Param("id") Long id, @Param("newPassword") String newPassword);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.email = :newEmail WHERE u.id = :id")
    void updateEmail(@Param("id") Long id, @Param("newEmail") String newEmail);
}
