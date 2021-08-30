package com.evgeni.service;

import com.evgeni.dto.UserDto;
import com.evgeni.entity.User;
import com.evgeni.exception.UserNotFoundException;
import com.evgeni.repository.UserRepository;
import com.evgeni.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exists."));

        return convertToDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto save(UserDto userDto) {

        Boolean ifExists = userRepository.existsByUsername(userDto.getUsername());

        if (ifExists) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        User user = convertToEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
        userDto.setId(user.getId());

        return userDto;
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null.");
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exists."));

        if (!existingUser.getUsername().equals(userDto.getUsername())) {
            existingUser.setUsername(userDto.getUsername());
        }

        if (!existingUser.getPassword().equals(userDto.getPassword())) {
            existingUser.setPassword(userDto.getPassword());
        }

        if (!existingUser.getFirstName().equals(userDto.getFirstName())) {
            existingUser.setFirstName(userDto.getFirstName());
        }

        if (!existingUser.getLastName().equals(userDto.getLastName())) {
            existingUser.setLastName(userDto.getLastName());
        }

        if (!existingUser.getEditedBy().equals(userDto.getEditedBy())) {
            existingUser.setEditedBy(userDto.getEditedBy());
        }

        if (!existingUser.getIsAdmin().equals(userDto.getIsAdmin())) {
            existingUser.setIsAdmin(userDto.getIsAdmin());
        }

        userRepository.save(existingUser);
        userDto.setId(existingUser.getId());

        return userDto;
    }

    @Override
    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User does not exists."));

        userRepository.deleteById(id);
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
