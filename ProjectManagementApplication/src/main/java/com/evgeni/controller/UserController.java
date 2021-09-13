package com.evgeni.controller;

import com.evgeni.dto.UserDto;
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
        return userService.findAll();
    }

    @GetMapping(path = "{userId}")
    public UserDto findById(@PathVariable("userId") Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public UserDto save(@Valid @RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping(path = "{userId}")
    public UserDto update(@PathVariable("userId") Long id, @Valid @RequestBody UserDto userDto) {
        return userService.update(id, userDto);
    }

    @DeleteMapping(path = "{userId}")
    public void delete(@PathVariable("userId") Long id) {
        userService.delete(id);
    }
}
