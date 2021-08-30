package com.evgeni.service.interfaces;

import com.evgeni.dto.TeamDto;

import java.util.List;

public interface TeamService {

    TeamDto findById(Long id);

    List<TeamDto> findAll();

    TeamDto save(TeamDto teamDto);

    TeamDto update(Long id, TeamDto teamDto);

    void delete(Long id);

    void assign(Long userId, Long teamId);

    void unassign(Long userId, Long teamId);

}
