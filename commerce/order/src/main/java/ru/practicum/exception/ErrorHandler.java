package ru.practicum.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exception.model.ErrorResponse;

@RestControllerAdvice
public class ErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.warn("NotFoundException: {}", e.getMessage(), e);
        return new ErrorResponse(
                e.getCause(),
                e.getStackTrace(),
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                e.getMessage(),
                e.getSuppressed(),
                e.getLocalizedMessage()
        );
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(final ConstraintViolationException e) throws NotAuthorizedUserException {
        if (e.getConstraintViolations().stream()
                .anyMatch(violation -> violation.getConstraintDescriptor()
                        .getAnnotation().annotationType().equals(NotBlank.class))) {
            return new ResponseEntity<>(
                    new ErrorResponse(
                            e.getCause(),
                            e.getStackTrace(),
                            HttpStatus.UNAUTHORIZED,
                            e.getMessage(),
                            e.getMessage(),
                            e.getSuppressed(),
                            e.getLocalizedMessage()
                    ), HttpStatus.UNAUTHORIZED
            );
        }

        return new ResponseEntity<>(
                new ErrorResponse(
                        e.getCause(),
                        e.getStackTrace(),
                        HttpStatus.BAD_REQUEST,
                        e.getMessage(),
                        e.getMessage(),
                        e.getSuppressed(),
                        e.getLocalizedMessage()
                ), HttpStatus.BAD_REQUEST
        );
    }
}
