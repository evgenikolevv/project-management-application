package com.evgeni.controller;

import com.evgeni.dto.ProjectDto;
import com.evgeni.exception.ProjectNotFoundException;
import com.evgeni.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping(path = "{projectId}")
    public ProjectDto findById(@PathVariable("projectId") Long id) {
        try {
            return projectService.findById(id);
        } catch (ProjectNotFoundException e) {
            throw new ProjectNotFoundException("Project with id: " + id + " is not found.");
        }
    }

    @GetMapping(path = "/all/{creatorId}")
    public List<ProjectDto> findAllByCreatorId(@PathVariable("creatorId") Long id) {
        try {
            return projectService.findAllByUserId(id);
        } catch (Exception e) {
            throw new ProjectNotFoundException("You don't own any projects.");
        }
    }

    @GetMapping(path = "/assigned/{userId}")
    public List<ProjectDto> findAllAssignedByUserId(@PathVariable("userId") Long id) {
        try {
            return projectService.findAllAssignedByUserId(id);
        } catch (Exception e) {
            throw new ProjectNotFoundException("There are no assigned projects.");
        }
    }

    @PostMapping
    public ProjectDto save(@Valid @RequestBody ProjectDto projectDto) {
        try {
            return projectService.save(projectDto);
        } catch (Exception e) {
            throw new IllegalArgumentException("Project fields must be valid.");
        }
    }

    @PutMapping(path = "{projectId}")
    public ProjectDto update(@PathVariable("projectId") Long id, @Valid @RequestBody ProjectDto projectDto) {
        try {
            return projectService.update(id, projectDto);
        } catch (ProjectNotFoundException e) {
            throw new ProjectNotFoundException("Project with id: " + id + " is not found.");
        }
    }

    @DeleteMapping(path = "{projectId}")
    public void delete(@PathVariable("projectId") Long id) {
        try {
            projectService.delete(id);
        } catch (ProjectNotFoundException e) {
            throw new ProjectNotFoundException("Project with id: " + id + " is not found.");
        }
    }

    @PostMapping("/assign")
    public void assign(@RequestParam Long teamId, @RequestParam Long projectId) {
        try {
            projectService.assign(teamId, projectId);
        } catch (Exception e) {
            throw new IllegalArgumentException("team with id: " + teamId + " is already assigned to project with id: " + projectId);
        }
    }

    @DeleteMapping("/unassign")
    public void unassign(Long teamId, Long projectId) {
        try {
            projectService.unassign(teamId, projectId);
        } catch (Exception e) {
            throw new IllegalArgumentException("team with id: " + teamId + " is not assigned to project with id: " + projectId);
        }
    }
}

