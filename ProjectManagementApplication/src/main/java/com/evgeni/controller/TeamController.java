package com.evgeni.controller;

import com.evgeni.dto.TeamDto;
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
        return teamService.findAll();
    }

    @GetMapping(path = "{teamId}")
    public TeamDto findById(@PathVariable("teamId") Long id) {
        return teamService.findById(id);
    }

    @PostMapping
    public TeamDto save(@Valid @RequestBody TeamDto teamDto) {
        return teamService.save(teamDto);
    }

    @PutMapping(path = "{teamId}")
    public TeamDto update(@PathVariable("teamId") Long id, @Valid @RequestBody TeamDto teamDto) {
        return teamService.update(id, teamDto);
    }

    @DeleteMapping(path = "{teamId}")
    public void delete(@PathVariable("teamId") Long id) {
        teamService.delete(id);
    }

    @PostMapping("/assign")
    public void assign(@RequestParam Long userId, @RequestParam Long teamId) {
        teamService.assign(userId, teamId);
    }

    @DeleteMapping("/unassign")
    public void unassign(Long userId, Long teamId) {
        teamService.unassign(userId, teamId);
    }
}
