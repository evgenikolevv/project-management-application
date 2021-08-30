package com.evgeni.service;

import com.evgeni.dto.ProjectDto;
import com.evgeni.entity.Project;
import com.evgeni.entity.User;
import com.evgeni.exception.ProjectNotFoundException;
import com.evgeni.repository.ProjectRepository;
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
public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private User user;
    private Project project;
    private ProjectDto projectDto;
    private final Long userId = 1L;
    private final Long projectId = 1L;

    @Before
    public void setUp() throws Exception {
        user = new User("admin", "adminpass", "Ivan",
                "Ivanov", 0L, 0L, true);

        project = new Project("Banking App",userId,user);

        projectDto = new ProjectDto("BankingApp",userId,userId);
    }

    @Test
    public void shouldFindById() {
        given(projectRepository.findById(projectId)).willReturn(Optional.of(project));

        projectService.findById(projectId);

        then(projectRepository).should().findById(projectId);
    }

    @Test
    public void shouldThrowExceptionWhenProjectIsNotFound() {

        assertThrows(ProjectNotFoundException.class, ()-> projectService.findById(projectId));
    }

    @Test
    public void shouldSaveProject() {
        given(modelMapper.map(projectDto, Project.class)).willReturn(project);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        projectService.save(projectDto);

        then(projectRepository).should().save(project);
    }

    @Test
    public void shouldUpdateProject() {
        given(projectRepository.findById(projectId)).willReturn(Optional.of(project));

        projectDto.setName("Fitness Application");
        projectService.update(projectId,projectDto);

        then(projectRepository).should().save(project);
    }

    @Test
    public void shouldDeleteProject() {
        given(projectRepository.findById(projectId)).willReturn(Optional.of(project));

        projectService.delete(projectId);

        then(projectRepository).should().deleteById(projectId);
    }

    @Test
    public void shouldNotDeleteWhenProjectIsNotFound() {

        assertThrows(ProjectNotFoundException.class, ()-> projectService.delete(projectId));
    }
}