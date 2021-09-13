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
        return projectService.findById(id);
    }

    @GetMapping(path = "/all/{creatorId}")
    public List<ProjectDto> findAllByCreatorId(@PathVariable("creatorId") Long id) {
        return projectService.findAllByUserId(id);
    }

    @GetMapping(path = "/assigned/{userId}")
    public List<ProjectDto> findAllAssignedByUserId(@PathVariable("userId") Long id) {
        return projectService.findAllAssignedByUserId(id);
    }

    @PostMapping
    public ProjectDto save(@Valid @RequestBody ProjectDto projectDto) {
        return projectService.save(projectDto);
    }

    @PutMapping(path = "{projectId}")
    public ProjectDto update(@PathVariable("projectId") Long id, @Valid @RequestBody ProjectDto projectDto) {
        return projectService.update(id, projectDto);
    }

    @DeleteMapping(path = "{projectId}")
    public void delete(@PathVariable("projectId") Long id) {
        projectService.delete(id);
    }

    @PostMapping("/assign")
    public void assign(@RequestParam Long teamId, @RequestParam Long projectId) {
        projectService.assign(teamId, projectId);
    }

    @DeleteMapping("/unassign")
    public void unassign(Long teamId, Long projectId) {
        projectService.unassign(teamId, projectId);
    }
}

