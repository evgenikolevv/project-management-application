package com.evgeni.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class TeamDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long editedBy;

    @NotNull
    private Long creatorId;

    private List<UserDto> users;

    public TeamDto(String name, Long editedBy, Long creatorId) {
        this.name = name;
        this.editedBy = editedBy;
        this.creatorId = creatorId;
    }
}

