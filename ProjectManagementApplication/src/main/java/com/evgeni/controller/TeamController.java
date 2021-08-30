package com.evgeni.controller;

import com.evgeni.dto.TeamDto;
import com.evgeni.exception.TeamNotFoundException;
import com.evgeni.service.interfaces.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teams")
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public List<TeamDto> findAll() {
        try {
            return teamService.findAll();
        } catch (Exception e) {
            throw new TeamNotFoundException("There are no teams.");
        }
    }

    @GetMapping(path = "{teamId}")
    public TeamDto findById(@PathVariable("teamId") Long id) {
        try {
            return teamService.findById(id);
        } catch (TeamNotFoundException e) {
            throw new TeamNotFoundException("Team with id: " + id + " is not found.");
        }
    }

    @PostMapping
    public TeamDto save(@Valid @RequestBody TeamDto teamDto) {
        try {
            return teamService.save(teamDto);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Team name is taken");
        }
    }

    @PutMapping(path = "{teamId}")
    public TeamDto update(@PathVariable("teamId") Long id, @Valid @RequestBody TeamDto teamDto) {
        try {
            return teamService.update(id, teamDto);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Team name is taken");
        }
    }

    @DeleteMapping(path = "{teamId}")
    public void delete(@PathVariable("teamId") Long id) {
        try {
            teamService.delete(id);
        } catch (TeamNotFoundException e) {
            throw new TeamNotFoundException("Team with id: " + id + " is not found.");
        }
    }

    @PostMapping("/assign")
    public void assign(@RequestParam Long userId, @RequestParam Long teamId) {
        try {
            teamService.assign(userId, teamId);
        } catch (Exception e) {
            throw new IllegalArgumentException("user with id: " + userId + " is already assigned to team with id: " + teamId);
        }
    }

    @DeleteMapping("/unassign")
    public void unassign(Long userId, Long teamId) {
        try {
            teamService.unassign(userId, teamId);
        } catch (Exception e) {
            throw new IllegalArgumentException("user with id: " + userId + " is not assigned to team with id: " + teamId);
        }
    }

}
