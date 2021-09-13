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
        return taskService.findById(id);
    }

    @GetMapping(path = "/all/{projectId}")
    public List<TaskDto> findAllByProjectId(@PathVariable("projectId") Long projectId) {
        return taskService.findAllByProjectId(projectId);
    }

    @GetMapping(path = "/assigned")
    public List<TaskDto> findAllAssignedByUserIdAndProjectId(@RequestParam Long userId, @RequestParam Long projectId) {
        return taskService.findAllAssignedByUserIdAndProjectId(userId, projectId);
    }

    @PostMapping
    public TaskDto save(@Valid @RequestBody TaskDto taskDto) {
        return taskService.save(taskDto);
    }

    @PutMapping(path = "{taskId}")
    public TaskDto update(@PathVariable("taskId") Long id, @Valid @RequestBody TaskDto taskDto) {
        return taskService.update(id, taskDto);
    }

    @DeleteMapping(path = "{taskId}")
    public void delete(@PathVariable("taskId") Long id) {
        taskService.delete(id);
    }

    @PostMapping("/assign")
    public void assign(@RequestParam Long userId, @RequestParam Long taskId) {
        taskService.assign(userId, taskId);
    }

    @DeleteMapping("/unassign")
    public void unassign(Long userId, Long taskId) {
        taskService.unassign(userId, taskId);
    }
}

