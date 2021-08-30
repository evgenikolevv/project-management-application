package com.evgeni.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ProjectDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long editedBy;

    @NotNull
    private Long creatorId;

    public ProjectDto(String name, Long editedBy, Long creatorId) {
        this.name = name;
        this.editedBy = editedBy;
        this.creatorId = creatorId;
    }
}
