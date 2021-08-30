package com.evgeni.repository;

import com.evgeni.entity.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    List<WorkLog> findAllByTaskId(Long taskId);
}
