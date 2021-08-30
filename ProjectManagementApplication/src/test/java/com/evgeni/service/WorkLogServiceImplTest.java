package com.evgeni.service;

import com.evgeni.dto.WorkLogDto;
import com.evgeni.entity.Project;
import com.evgeni.entity.Task;
import com.evgeni.entity.User;
import com.evgeni.entity.WorkLog;
import com.evgeni.exception.WorkLogNotFoundException;
import com.evgeni.repository.TaskRepository;
import com.evgeni.repository.UserRepository;
import com.evgeni.repository.WorkLogRepository;
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
public class WorkLogServiceImplTest {

    @Mock
    private WorkLogRepository workLogRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private WorkLogServiceImpl workLogService;

    private User user;
    private Task task;
    private WorkLog workLog;
    private WorkLogDto workLogDto;
    private Long userId = 1L;
    private Long taskId = 1L;
    private Long workLogId =1L;

    @Before
    public void setUp() throws Exception {
        user = new User("admin", "adminpass", "Ivan",
                "Ivanov", 0L, 0L, true);

        Project project = new Project("Banking App",userId,user);

        task = new Task ("Database Model", "DDL,Tables,Relationships",
                userId, "NOT COMPLETED",user,project);

        workLog = new WorkLog("Database Model", "fixing tables",
                userId,task,user);

        workLogDto = new WorkLogDto("Database Model", "DDL,Tables,Relationships",
                taskId,userId,userId);
    }

    @Test
    public void shouldFindById() {
        given(workLogRepository.findById(workLogId)).willReturn(Optional.of(workLog));

        workLogService.findById(workLogId);

        then(workLogRepository).should().findById(workLogId);
    }

    @Test
    public void shouldThrowExceptionWhenIsNotFoundById() {

        assertThrows(WorkLogNotFoundException.class, () -> workLogService.findById(workLogId));
    }

    @Test
    public void shouldSaveWorkLog() {
        given(modelMapper.map(workLogDto, WorkLog.class)).willReturn(workLog);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(taskRepository.findById(taskId)).willReturn(Optional.of(task));

        workLogService.save(workLogDto);

        then(workLogRepository).should().save(workLog);
    }

    @Test
    public void shouldUpdateWorkLog() {
        given(workLogRepository.findById(workLogId)).willReturn(Optional.of(workLog));

        workLogDto.setDescription("fixed tables.");
        workLogService.update(workLogId,workLogDto);

        then(workLogRepository).should().save(workLog);
    }

    @Test
    public void shouldDeleteWorkLog() {
        given(workLogRepository.findById(workLogId)).willReturn(Optional.of(workLog));

        workLogService.delete(workLogId);

        then(workLogRepository).should().deleteById(workLogId);
    }

    @Test
    public void shouldNotDeleteWhenWorkLogIsNotFound() {

        assertThrows(WorkLogNotFoundException.class, ()-> workLogService.delete(workLogId));
    }
}