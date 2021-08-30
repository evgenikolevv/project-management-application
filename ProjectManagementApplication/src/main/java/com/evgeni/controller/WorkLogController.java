package com.evgeni.controller;

import com.evgeni.dto.WorkLogDto;
import com.evgeni.exception.TaskNotFoundException;
import com.evgeni.exception.UserNotFoundException;
import com.evgeni.exception.WorkLogNotFoundException;
import com.evgeni.service.interfaces.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/worklogs")
@RequiredArgsConstructor
public class WorkLogController {

    private final WorkLogService workLogService;

    @GetMapping(path = "{workLogId}")
    public WorkLogDto findById(@PathVariable("workLogId") Long id) {
        try {
            return workLogService.findById(id);
        } catch (WorkLogNotFoundException e) {
            throw new WorkLogNotFoundException("Worklog with id :" + id + " is not found.");
        }
    }

    @GetMapping(path = "/all/{taskId}")
    public List<WorkLogDto> findAllByProjectId(@PathVariable("taskId") Long taskId) {
        try {
            return workLogService.findAllByTaskId(taskId);
        } catch (WorkLogNotFoundException e) {
            throw new WorkLogNotFoundException("There are no worklogs.");
        }
    }

    @PostMapping
    public WorkLogDto save(@Valid @RequestBody WorkLogDto workLogDto) {
        try {
            return workLogService.save(workLogDto);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Worklog fields must be valid.");
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User with id :" + workLogDto.getCreatorId() + " is not found.");
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException("Task with id :" + workLogDto.getTaskId() + " is not found.");
        }
    }

    @PutMapping(path = "{workLogId}")
    public WorkLogDto update(@PathVariable("workLogId") Long id, @Valid @RequestBody WorkLogDto workLogDto) {
        try {
            return workLogService.update(id, workLogDto);
        } catch (WorkLogNotFoundException e) {
            throw new WorkLogNotFoundException("Worklog with id :" + id + " is not found.");
        }
    }

    @DeleteMapping(path = "{workLogId}")
    public void delete(@PathVariable("workLogId") Long id) {
        try {
            workLogService.delete(id);
        } catch (WorkLogNotFoundException e) {
            throw new WorkLogNotFoundException("Worklog with id :" + id + " is not found.");
        }
    }
}

