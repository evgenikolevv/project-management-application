package com.evgeni.repository;

import com.evgeni.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    String FIND_ALL_ASSIGNED_PROJECT_BY_USER_ID = "SELECT DISTINCT Projects.id, Projects.name, Projects.creation_date, " +
            "Projects.updated_date, Projects.updated_by, Projects.user_id FROM Users " +
            "JOIN Users_Teams ON Users.id = Users_Teams.user_id " +
            "JOIN Teams ON Users_Teams.team_id = Teams.id " +
            "JOIN Teams_Projects ON Teams.id = Teams_Projects.team_id " +
            "JOIN Projects ON Teams_Projects.project_id = Projects.id WHERE Users.id = :id";

    List<Project> findAllByCreatorId(Long id);

    @Modifying
    @Query(value = FIND_ALL_ASSIGNED_PROJECT_BY_USER_ID, nativeQuery = true)
    List<Project> findAllAssignedProjectsByUserId(@Param("id") Long id);

    @Modifying
    @Query(value = "INSERT  INTO Teams_Projects(team_id,project_id) VALUES(:teamId,:projectId)", nativeQuery = true)
    void assign(@Param("teamId") Long teamId, @Param("projectId") Long projectId);

    @Modifying
    @Query(value = "DELETE FROM Teams_Projects WHERE team_id = :teamId AND project_id = :projectId", nativeQuery = true)
    void unassign(@Param("teamId") Long teamId, @Param("projectId") Long projectId);
}
