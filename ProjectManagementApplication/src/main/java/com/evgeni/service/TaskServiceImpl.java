package com.evgeni.service;

import com.evgeni.dto.TaskDto;
import com.evgeni.entity.Task;
import com.evgeni.exception.ProjectNotFoundException;
import com.evgeni.exception.TaskNotFoundException;
import com.evgeni.exception.UserNotFoundException;
import com.evgeni.repository.ProjectRepository;
import com.evgeni.repository.TaskRepository;
import com.evgeni.repository.UserRepository;
import com.evgeni.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    @Override
    public TaskDto findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task does not exists."));

        return convertToDto(task);
    }

    @Override
    public List<TaskDto> findAllByProjectId(Long projectId) {
        return taskRepository.findAllByProjectId(projectId)
                .stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> findAllAssignedByUserIdAndProjectId(Long userId, Long projectId) {
        return taskRepository.findAllByUserIdAndProjectId(userId, projectId)
                .stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto save(TaskDto taskDto) {

        Task task = convertToEntity(taskDto);

        taskRepository.save(task);
        taskDto.setId(task.getId());

        return taskDto;
    }

    @Override
    public TaskDto update(Long id, TaskDto taskDto) {

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task does not exists."));

        if (!existingTask.getTitle().equals(taskDto.getTitle())) {
            existingTask.setTitle(taskDto.getTitle());
        }

        if (!existingTask.getDescription().equals(taskDto.getDescription())) {
            existingTask.setDescription(taskDto.getDescription());
        }

        if (!existingTask.getStatus().equals(taskDto.getStatus())) {
            existingTask.setStatus(taskDto.getStatus());
        }

        if (!existingTask.getEditedBy().equals(taskDto.getEditedBy())) {
            existingTask.setEditedBy(taskDto.getEditedBy());
        }

        taskRepository.save(existingTask);
        taskDto.setId(existingTask.getId());
        taskDto.setCreatorId(existingTask.getCreator().getId());
        taskDto.setProjectId(existingTask.getProject().getId());

        return taskDto;
    }

    @Override
    public void delete(Long id) {
        taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task does not exists."));

        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void assign(Long userId, Long taskId) {

        taskRepository.assign(userId, taskId);
    }

    @Override
    @Transactional
    public void unassign(Long userId, Long taskId) {

        taskRepository.unassign(userId, taskId);
    }

    private TaskDto convertToDto(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }

    private Task convertToEntity(TaskDto taskDto) {
        Task task = modelMapper.map(taskDto, Task.class);
        task.setCreator(userRepository.findById(taskDto.getCreatorId())
                .orElseThrow(() -> new UserNotFoundException("User does not exists.")));

        task.setProject(projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project does not exists.")));

        return task;
    }
}
