package ru.netology.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginData {
    private final String login;
    private final String password;
    private final String status;
}
