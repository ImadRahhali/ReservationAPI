package com.example.studyRoomsINPT.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @NotNull
    private String newUsername;

    @NotNull
    private String newPassword;

    @NotNull
    private String newEmail;


}