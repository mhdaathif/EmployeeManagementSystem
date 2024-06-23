package com.aathif.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ApplicationCustomException extends RuntimeException {
    private HttpStatus status;
    private String code;
    private String message;
}
