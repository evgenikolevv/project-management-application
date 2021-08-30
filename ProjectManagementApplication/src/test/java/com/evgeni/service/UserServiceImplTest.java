package com.evgeni.service;

import com.evgeni.dto.UserDto;
import com.evgeni.entity.User;
import com.evgeni.exception.UserNotFoundException;
import com.evgeni.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private UserDto userDto;

    private final Long userId = 1L;

    @Before
    public void setUp() throws Exception {

        user = new User("admin", "adminpass", "Ivan",
                "Ivanov", 0L, 0L, true);

        userDto = new UserDto("admin", "adminpass", "Ivan",
                "Ivanov", true, 0L, 0L);
    }

    @Test
    public void shouldFindById() {
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        userService.findById(userId);

        then(userRepository).should().findById(userId);
    }

    @Test
    public void shouldThrowUserNotFoundExceptionWhenUserIsNotFoundById() {

        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
    }

    @Test
    public void shouldSaveUser() {
        given(userRepository.existsByUsername(userDto.getUsername())).willReturn(false);
        given(modelMapper.map(userDto, User.class)).willReturn(user);
        given(passwordEncoder.encode(userDto.getPassword())).willReturn("$2a$10$BePSayuQm3AS7NebF7npN.WMl1jeN6xWKh9Nph/Qe3cjFcZpGb/uq");

        userService.save(userDto);

        then(userRepository).should().save(user);
    }

    @Test
    public void shouldNotSaveUserWhenUsernameIsTaken() {
        given(userRepository.existsByUsername(userDto.getUsername())).willReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.save(userDto));
    }

    @Test
    public void shouldUpdateUser() {
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        userDto.setUsername("user1");
        userService.update(userId, userDto);
        then(userRepository).should().save(user);
    }

    @Test
    public void shouldNotUpdateWhenUserIsNotFound() {

        assertThrows(UserNotFoundException.class, () -> userService.update(userId, userDto));
    }

    @Test
    public void shouldDeleteUser() {
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        userService.delete(userId);

        then(userRepository).should().deleteById(userId);
    }

    @Test
    public void shouldNotDeleteWhenUserIsNotFound() {

        assertThrows(UserNotFoundException.class, () -> userService.delete(userId));
    }
}