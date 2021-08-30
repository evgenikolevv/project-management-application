package com.evgeni.service;

import com.evgeni.dto.TaskDto;
import com.evgeni.entity.Project;
import com.evgeni.entity.Task;
import com.evgeni.entity.User;
import com.evgeni.exception.TaskNotFoundException;
import com.evgeni.repository.ProjectRepository;
import com.evgeni.repository.TaskRepository;
import com.evgeni.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private User user;
    private Project project;
    private Task task;
    private TaskDto taskDto;
    private final Long userId = 1L;
    private final Long projectId = 1L;
    private final Long taskId = 1L;

    @Before
    public void setUp() throws Exception {
        user = new User("admin", "adminpass", "Ivan",
                "Ivanov", 0L, 0L, true);

        project = new Project("Banking App",userId,user);

        task = new Task ("Database Model", "DDL,Tables,Relationships",
                userId, "NOT COMPLETED",user,project);

        taskDto = new TaskDto("Database Model", "DDL,Tables,Relationships",
                "NOT COMPLETED", userId,userId,projectId);
    }

    @Test
    public void shouldFindById() {
        given(taskRepository.findById(taskId)).willReturn(Optional.of(task));

        taskService.findById(taskId);

        then(taskRepository).should().findById(taskId);
    }

    @Test
    public void shouldThrowExceptionWhenTaskIsNotFound() {

        assertThrows(TaskNotFoundException.class, ()-> taskService.findById(taskId));
    }

    @Test
    public void shouldSaveTask() {
        given(modelMapper.map(taskDto, Task.class)).willReturn(task);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(projectRepository.findById(projectId)).willReturn(Optional.of(project));

        taskService.save(taskDto);

        then(taskRepository).should().save(task);
    }

    @Test
    public void shouldUpdateTask() {
        given(taskRepository.findById(taskId)).willReturn(Optional.of(task));

        taskDto.setStatus("COMPLETED");
        taskService.update(projectId,taskDto);

        then(taskRepository).should().save(task);
    }

    @Test
    public void shouldDeleteTask() {
        given(taskRepository.findById(taskId)).willReturn(Optional.of(task));

        taskService.delete(taskId);

        then(taskRepository).should().deleteById(taskId);
    }

    @Test
    public void shouldNotDeleteWhenTaskIsNotFound() {

        assertThrows(TaskNotFoundException.class, ()-> taskService.delete(taskId));
    }
}