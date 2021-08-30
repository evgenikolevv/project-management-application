package com.evgeni.handler;

import com.evgeni.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorInfo> error(final Exception exception, final HttpStatus httpStatus,
                                            HttpServletRequest request) {

        return new ResponseEntity<>(new ErrorInfo(exception, request.getRequestURI()), httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorInfo> handleIllegalArgumentException(HttpServletRequest request,
                                                                    IllegalArgumentException e) {
        return error(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleUserNotFountException(HttpServletRequest request,
                                                                 UserNotFoundException e) {

        return error(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = TeamNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleTeamNotFountException(HttpServletRequest request,
                                                                 TeamNotFoundException e) {

        return error(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = ProjectNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleProjectNotFountException(HttpServletRequest request,
                                                                 ProjectNotFoundException e) {

        return error(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = TaskNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleTaskNotFountException(HttpServletRequest request,
                                                                    TaskNotFoundException e) {

        return error(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = WorkLogNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleWorkLogNotFountException(HttpServletRequest request,
                                                                 WorkLogNotFoundException e) {

        return error(e, HttpStatus.BAD_REQUEST, request);
    }
}
