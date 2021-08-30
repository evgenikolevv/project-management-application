package com.evgeni.controller;

import com.evgeni.dto.TaskDto;
import com.evgeni.exception.ProjectNotFoundException;
import com.evgeni.exception.TaskNotFoundException;
import com.evgeni.exception.UserNotFoundException;
import com.evgeni.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping(path = "{taskId}")
    public TaskDto findById(@PathVariable("taskId") Long id) {
        try {
            return taskService.findById(id);
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException("Task with id: " + id + " is not found.");
        }
    }

    @GetMapping(path = "/all/{projectId}")
    public List<TaskDto> findAllByProjectId(@PathVariable("projectId") Long projectId) {
        try {
            return taskService.findAllByProjectId(projectId);
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException("There are no tasks.");
        }
    }

    @GetMapping(path = "/assigned")
    public List<TaskDto> findAllAssignedByUserIdAndProjectId(@RequestParam Long userId, @RequestParam Long projectId) {
        try {
            return taskService.findAllAssignedByUserIdAndProjectId(userId, projectId);
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException("There are no assigned tasks.");
        }
    }

    @PostMapping
    public TaskDto save(@Valid @RequestBody TaskDto taskDto) {
        try {
            return taskService.save(taskDto);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Task fields must be valid.");
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User with id :" + taskDto.getCreatorId() + " does not exists.");
        } catch (ProjectNotFoundException e) {
            throw new ProjectNotFoundException("Project with id :" + taskDto.getProjectId() + " does not exists.");
        }
    }

    @PutMapping(path = "{taskId}")
    public TaskDto update(@PathVariable("taskId") Long id, @Valid @RequestBody TaskDto taskDto) {
        try {
            return taskService.update(id, taskDto);
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException("Task with id: " + id + " is not found.");
        }
    }

    @DeleteMapping(path = "{taskId}")
    public void delete(@PathVariable("taskId") Long id) {
        try {
            taskService.delete(id);
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException("Task with id: " + id + " is not found.");
        }
    }

    @PostMapping("/assign")
    public void assign(@RequestParam Long userId, @RequestParam Long taskId) {
        try {
            taskService.assign(userId, taskId);
        } catch (Exception e) {
            throw new IllegalArgumentException("user with id: " + userId + " is already assigned to task with id: " + taskId);
        }
    }

    @DeleteMapping("/unassign")
    public void unassign(Long userId, Long taskId) {
        try {
            taskService.unassign(userId, taskId);
        } catch (Exception e) {
            throw new IllegalArgumentException("user with id: " + userId + " is not assigned to task with id: " + taskId);
        }
    }
}

