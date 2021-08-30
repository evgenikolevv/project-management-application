package com.evgeni.service.interfaces;

import com.evgeni.dto.WorkLogDto;

import java.util.List;

public interface WorkLogService {

    WorkLogDto findById(Long id);

    List<WorkLogDto> findAllByTaskId(Long id);

    WorkLogDto save(WorkLogDto workLogDto);

    WorkLogDto update(Long id, WorkLogDto workLog);

    void delete(Long id);
}
