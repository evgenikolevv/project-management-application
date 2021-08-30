package com.evgeni.repository;

import com.evgeni.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    String FIND_ALL_BY_USER_ID_AND_PROJECT_ID = "SELECT Tasks.id, Tasks.title, Tasks.description, " +
            "Tasks.creation_date, Tasks.updated_date, Tasks.updated_by, Tasks.status, " +
            "Tasks.user_id, Tasks.project_id FROM Tasks JOIN Users_Tasks ON Tasks.id = Users_Tasks.task_id " +
            "JOIN Users ON Users_Tasks.user_id = Users.id WHERE Users.id = :userId AND Tasks.project_id = :projectId";

    @Modifying
    @Query(value = "INSERT INTO Users_Tasks(user_id,task_id) VALUES(:userId,:taskId)", nativeQuery = true)
    void assign(@Param("userId") Long userId, @Param("taskId") Long taskId);

    @Modifying
    @Query(value = "DELETE FROM Users_Tasks WHERE user_id = :userId AND task_id = :taskId", nativeQuery = true)
    void unassign(@Param("userId") Long userId, @Param("taskId") Long taskId);

    List<Task> findAllByProjectId(Long projectId);

    @Modifying
    @Query(value = FIND_ALL_BY_USER_ID_AND_PROJECT_ID, nativeQuery = true)
    List<Task> findAllByUserIdAndProjectId(@Param("userId") Long userId, @Param("projectId") Long projectId);
}
