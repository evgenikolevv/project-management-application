package com.evgeni.service;

import com.evgeni.dto.WorkLogDto;
import com.evgeni.entity.WorkLog;
import com.evgeni.exception.TaskNotFoundException;
import com.evgeni.exception.UserNotFoundException;
import com.evgeni.exception.WorkLogNotFoundException;
import com.evgeni.repository.TaskRepository;
import com.evgeni.repository.UserRepository;
import com.evgeni.repository.WorkLogRepository;
import com.evgeni.service.interfaces.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkLogServiceImpl implements WorkLogService {

    private final WorkLogRepository workLogRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Override
    public WorkLogDto findById(Long id) {
        WorkLog workLog = workLogRepository.findById(id)
                .orElseThrow(() -> new WorkLogNotFoundException("WorkLog does not exists."));

        return convertToDto(workLog);
    }

    @Override
    public List<WorkLogDto> findAllByTaskId(Long id) {
        return workLogRepository.findAllByTaskId(id)
                .stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkLogDto save(WorkLogDto workLogDto) {

        WorkLog workLog = convertToEntity(workLogDto);

        workLogRepository.save(workLog);
        workLogDto.setId(workLog.getId());

        return workLogDto;
    }

    @Override
    public WorkLogDto update(Long id, WorkLogDto workLogDto) {

        WorkLog existingWorkLog = workLogRepository.findById(id)
                .orElseThrow(() -> new WorkLogNotFoundException("WorkLog does not exists."));

        if (!existingWorkLog.getTitle().equals(workLogDto.getTitle())) {
            existingWorkLog.setTitle(workLogDto.getTitle());
        }

        if (!existingWorkLog.getDescription().equals(workLogDto.getDescription())) {
            existingWorkLog.setDescription(workLogDto.getDescription());
        }


        if (!existingWorkLog.getEditedBy().equals(workLogDto.getEditedBy())) {
            existingWorkLog.setEditedBy(workLogDto.getEditedBy());
        }

        workLogRepository.save(existingWorkLog);
        workLogDto.setId(existingWorkLog.getId());
        workLogDto.setCreatorId(existingWorkLog.getCreator().getId());
        workLogDto.setTaskId(existingWorkLog.getTask().getId());

        return workLogDto;
    }

    @Override
    public void delete(Long id) {
        workLogRepository.findById(id)
                .orElseThrow(() -> new WorkLogNotFoundException("WorkLog does not exists."));

        workLogRepository.deleteById(id);
    }

    private WorkLogDto convertToDto(WorkLog workLog) {
        return modelMapper.map(workLog, WorkLogDto.class);
    }

    private WorkLog convertToEntity(WorkLogDto workLogDto) {
        WorkLog workLog = modelMapper.map(workLogDto, WorkLog.class);
        workLog.setCreator(userRepository.findById(workLogDto.getCreatorId())
                .orElseThrow(() -> new UserNotFoundException("User does not exists.")));

        workLog.setTask(taskRepository.findById(workLogDto.getTaskId())
                .orElseThrow(() -> new TaskNotFoundException("Task does not exists.")));

        return workLog;
    }
}

