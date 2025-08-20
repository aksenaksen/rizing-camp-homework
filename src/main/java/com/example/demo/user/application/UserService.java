package com.example.demo.user.application;

import com.example.demo.user.domain.User;
import com.example.demo.user.infra.UserRepository;
import com.example.demo.user.presentation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto save(UserCreateRequestDto request) {
        User user = User.create(
                request.getName(),
                request.getEmail(),
                request.getPhoneNumber()
        );
        User saved = userRepository.save(user);
        return UserResponseDto.from(saved);
    }
}
