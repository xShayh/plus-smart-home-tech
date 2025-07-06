package ru.yandex.practicum.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidUsernameException extends ErrorResponse {
    public InvalidUsernameException(String username) {
        super(HttpStatus.UNAUTHORIZED,
                "Некорректное имя пользователя. Введённое имя: " + "'" + username + "'");
    }
}