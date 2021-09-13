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
        return workLogService.findById(id);
    }

    @GetMapping(path = "/all/{taskId}")
    public List<WorkLogDto> findAllByProjectId(@PathVariable("taskId") Long taskId) {
        return workLogService.findAllByTaskId(taskId);
    }

    @PostMapping
    public WorkLogDto save(@Valid @RequestBody WorkLogDto workLogDto) {
        return workLogService.save(workLogDto);
    }

    @PutMapping(path = "{workLogId}")
    public WorkLogDto update(@PathVariable("workLogId") Long id, @Valid @RequestBody WorkLogDto workLogDto) {
        return workLogService.update(id, workLogDto);
    }

    @DeleteMapping(path = "{workLogId}")
    public void delete(@PathVariable("workLogId") Long id) {
        workLogService.delete(id);
    }
}

