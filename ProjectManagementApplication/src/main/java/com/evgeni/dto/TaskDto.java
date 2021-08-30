package com.evgeni.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class TaskDto {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String status;

    @NotNull
    private Long editedBy;

    @NotNull
    private Long creatorId;

    @NotNull
    private Long projectId;

    private List<UserDto> assignees;

    public TaskDto(String title, String description, String status, Long editedBy, Long creatorId, Long projectId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.editedBy = editedBy;
        this.creatorId = creatorId;
        this.projectId = projectId;
    }
}
