package com.evgeni.repository;

import com.evgeni.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Modifying
    @Query(value = "INSERT INTO Users_Teams(user_id,team_id) VALUES(:userId,:teamId)", nativeQuery = true)
    void assign(@Param("userId") Long userId, @Param("teamId") Long teamId);

    @Modifying
    @Query(value = "DELETE FROM Users_Teams WHERE user_id = :userId AND team_id = :teamId", nativeQuery = true)
    void unassign(@Param("userId") Long userId, @Param("teamId") Long teamId);

    Boolean existsByName(String name);

}
