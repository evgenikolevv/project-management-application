package com.evgeni.controller;

import com.evgeni.dto.UserDto;
import com.evgeni.exception.UserNotFoundException;
import com.evgeni.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        try {
            return userService.findAll();
        } catch (Exception e) {
            throw new UserNotFoundException("There are no users.");
        }
    }

    @GetMapping(path = "{userId}")
    public UserDto findById(@PathVariable("userId") Long id) {
        try {
            return userService.findById(id);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User with id: " + id + " is not found.");
        }
    }

    @PostMapping
    public UserDto save(@Valid @RequestBody UserDto userDto) {
        try {
            return userService.save(userDto);
        } catch (Exception e) {
            throw new IllegalArgumentException("User fields must not be null");
        }
    }

    @PutMapping(path = "{userId}")
    public UserDto update(@PathVariable("userId") Long id, @Valid @RequestBody UserDto userDto) {
        try {
            return userService.update(id, userDto);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("id and user must not be null");
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User with id: " + id + " is not found.");
        }
    }

    @DeleteMapping(path = "{userId}")
    public void delete(@PathVariable("userId") Long id) {
        try {
            userService.delete(id);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User with id: " + id + " is not found.");
        }
    }
}
