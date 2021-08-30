package com.evgeni.service;

import com.evgeni.dto.ProjectDto;
import com.evgeni.entity.Project;
import com.evgeni.exception.ProjectNotFoundException;
import com.evgeni.exception.UserNotFoundException;
import com.evgeni.repository.ProjectRepository;
import com.evgeni.repository.UserRepository;
import com.evgeni.service.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProjectDto findById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project does not exists."));

        return convertToDto(project);
    }

    @Override
    public List<ProjectDto> findAllByUserId(Long id) {
        return projectRepository.findAllByCreatorId(id)
                .stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDto> findAllAssignedByUserId(Long id) {
        return projectRepository.findAllAssignedProjectsByUserId(id)
                .stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto save(ProjectDto projectDto) {

        Project project = convertToEntity(projectDto);

        projectRepository.save(project);
        projectDto.setId(project.getId());

        return projectDto;
    }

    @Override
    public ProjectDto update(Long id, ProjectDto projectDto) {

        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project does not exists."));

        if (!existingProject.getName().equals(projectDto.getName())) {
            existingProject.setName(projectDto.getName());
        }

        if (!existingProject.getEditedBy().equals(projectDto.getEditedBy())) {
            existingProject.setEditedBy(projectDto.getEditedBy());
        }

        projectRepository.save(existingProject);
        projectDto.setId(existingProject.getId());
        projectDto.setCreatorId(existingProject.getCreator().getId());

        return projectDto;
    }

    @Override
    public void delete(Long id) {
      projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project does not exists."));

        projectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void assign(Long teamId, Long projectId) {

        projectRepository.assign(teamId, projectId);
    }

    @Override
    @Transactional
    public void unassign(Long teamId, Long projectId) {

        projectRepository.unassign(teamId, projectId);
    }

    private ProjectDto convertToDto(Project project) {
        return modelMapper.map(project, ProjectDto.class);
    }

    private Project convertToEntity(ProjectDto projectDto) {
        Project project = modelMapper.map(projectDto, Project.class);
        project.setCreator(userRepository.findById(projectDto.getCreatorId())
                .orElseThrow(() -> new UserNotFoundException("User does not exists.")));

        return project;
    }
}
