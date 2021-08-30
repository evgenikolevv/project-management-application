package com.evgeni.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Boolean isAdmin;

    @NotNull
    private Long editedBy;

    @NotNull
    private Long creatorId;

    public UserDto(String username, String password, String firstName, String lastName, Boolean isAdmin,
                   Long editedBy, Long creatorId) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
        this.editedBy = editedBy;
        this.creatorId = creatorId;
    }
}
