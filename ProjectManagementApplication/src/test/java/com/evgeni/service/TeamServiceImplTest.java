package com.evgeni.service;

import com.evgeni.dto.TeamDto;
import com.evgeni.entity.Team;
import com.evgeni.entity.User;
import com.evgeni.exception.TeamNotFoundException;
import com.evgeni.repository.TeamRepository;
import com.evgeni.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TeamServiceImpl teamService;

    private Team team;

    private TeamDto teamDto;

    private User user;

    private final Long userId = 1L;

    private final Long teamId = 1L;

    @Before
    public void setUp() throws Exception {
        user = new User("admin", "adminpass", "Ivan",
                "Ivanov", 0L, 0L, true);


        team = new Team("testers", 1L, user);

        teamDto = new TeamDto("testers", 1L, 1L);
    }

    @Test
    public void shouldFindById() {
        given(teamRepository.findById(teamId)).willReturn(Optional.of(team));

        teamService.findById(teamId);

        then(teamRepository).should().findById(teamId);
    }

    @Test
    public void shouldThrowExceptionWhenTeamIsNotFound() {

        assertThrows(TeamNotFoundException.class, () -> teamService.findById(teamId));
    }

    @Test
    public void shouldSaveTeam() {
        given(teamRepository.existsByName(teamDto.getName())).willReturn(false);
        given(modelMapper.map(teamDto, Team.class)).willReturn(team);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        teamService.save(teamDto);

        then(teamRepository).should().save(team);
    }

    @Test
    public void shouldNotSaveTeamWhenNameIsTaken() {
        given(teamRepository.existsByName(teamDto.getName())).willReturn(true);

        assertThrows(IllegalArgumentException.class, ()-> teamService.save(teamDto));
    }

    @Test
    public void shouldUpdateTeam() {
        given(teamRepository.findById(teamId)).willReturn(Optional.of(team));

        teamDto.setName("Developers");
        teamService.update(teamId,teamDto);
        then(teamRepository).should().save(team);
    }

    @Test
    public void shouldNotUpdateTeamWhenTeamIsNotFound() {

        assertThrows(TeamNotFoundException.class, ()-> teamService.update(teamId,teamDto));
    }

    @Test
    public void shouldDeleteTeam() {
        given(teamRepository.findById(teamId)).willReturn(Optional.of(team));

        teamService.delete(teamId);

        then(teamRepository).should().deleteById(teamId);
    }

    @Test
    public void shouldNotDeleteWhenTeamIsNotFound() {

        assertThrows(TeamNotFoundException.class, () -> teamService.delete(teamId));
    }
}