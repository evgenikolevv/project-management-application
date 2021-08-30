package com.evgeni.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class WorkLogDto {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private Long taskId;

    @NotNull
    private Long editedBy;

    @NotNull
    private Long creatorId;

    public WorkLogDto(String title, String description, Long taskId, Long editedBy, Long creatorId) {
        this.title = title;
        this.description = description;
        this.taskId = taskId;
        this.editedBy = editedBy;
        this.creatorId = creatorId;
    }
}
