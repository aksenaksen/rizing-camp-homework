package com.example.demo.user.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateRequestDto {
    private String name;
    private String email;
    private String phoneNumber;
}
