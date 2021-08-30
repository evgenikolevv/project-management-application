package com.evgeni.service;

import com.evgeni.dto.TeamDto;
import com.evgeni.entity.Team;
import com.evgeni.exception.TeamNotFoundException;
import com.evgeni.exception.UserNotFoundException;
import com.evgeni.repository.TeamRepository;
import com.evgeni.repository.UserRepository;
import com.evgeni.service.interfaces.TeamService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public TeamDto findById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException("Team does not exists."));

        return convertToDto(team);
    }

    @Override
    public List<TeamDto> findAll() {
        return teamRepository.findAll()
                .stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TeamDto save(TeamDto teamDto) {

        Boolean ifExists = teamRepository.existsByName(teamDto.getName());

        if (ifExists) {
            throw new IllegalArgumentException("Name is already taken.");
        }

        Team team = convertToEntity(teamDto);
        teamRepository.save(team);
        teamDto.setId(team.getId());

        return teamDto;
    }

    @Override
    public TeamDto update(Long id, TeamDto teamDto) {

        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException("Team does not exists."));

        Boolean ifExists = teamRepository.existsByName(teamDto.getName());

        if (ifExists) {
            throw new IllegalArgumentException("Name is already taken.");
        }

        if (!existingTeam.getName().equals(teamDto.getName())) {
            existingTeam.setName(teamDto.getName());
        }

        if (!existingTeam.getEditedBy().equals(teamDto.getEditedBy())) {
            existingTeam.setEditedBy(teamDto.getEditedBy());
        }

        teamRepository.save(existingTeam);
        teamDto.setId(existingTeam.getId());
        teamDto.setCreatorId(existingTeam.getCreator().getId());

        return teamDto;
    }

    @Override
    public void delete(Long id) {
        teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException("Team does not exists."));

        teamRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void assign(Long userId, Long teamId) {

        teamRepository.assign(userId, teamId);
    }

    @Override
    @Transactional
    public void unassign(Long userId, Long teamId) {

        teamRepository.unassign(userId, teamId);
    }


    private TeamDto convertToDto(Team team) {

        return modelMapper.map(team, TeamDto.class);
    }

    private Team convertToEntity(TeamDto teamDto) {
        Team team = modelMapper.map(teamDto, Team.class);
        team.setCreator(userRepository.findById(teamDto.getCreatorId())
                .orElseThrow(() -> new UserNotFoundException("User does not exists.")));

        return team;
    }
}
