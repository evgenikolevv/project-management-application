package com.evgeni.service.interfaces;

import com.evgeni.dto.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto findById(Long id);

    List<TaskDto> findAllByProjectId(Long projectId);

    List<TaskDto> findAllAssignedByUserIdAndProjectId(Long userId, Long projectId);

    TaskDto save(TaskDto taskDto);

    TaskDto update(Long id, TaskDto task);

    void delete(Long id);

    void assign(Long userId, Long taskId);

    void unassign(Long userId, Long taskId);
}
