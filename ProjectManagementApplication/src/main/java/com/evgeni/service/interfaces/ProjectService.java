package com.evgeni.service.interfaces;

import com.evgeni.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    ProjectDto findById(Long id);

    List<ProjectDto> findAllByUserId(Long id);

    List<ProjectDto> findAllAssignedByUserId(Long id);

    ProjectDto save(ProjectDto projectDto);

    ProjectDto update(Long id, ProjectDto projectDto);

    void delete(Long id);

    void assign(Long teamId, Long projectId);

    void unassign(Long teamId, Long projectId);
}
