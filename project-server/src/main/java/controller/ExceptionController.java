package controller;

import exception.ChannelIdException;
import exception.EmailException;
import exception.UserInfoException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email does not have the required format")
    @ExceptionHandler(EmailException.class)
    public void emailException() {}

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "A regular user can only change its own information")
    @ExceptionHandler(UserInfoException.class)
    public void userInfoException() {}

    @ResponseStatus(value=HttpStatus.CONFLICT, reason="Data integrity violation")  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict() {
        // Nothing to do
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public void databaseError() {

    }

    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Channel ID should be 15 characters long")
    @ExceptionHandler(ChannelIdException.class)
    public void channelIdError() {}

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex) {
        ex.printStackTrace();
    }
}
