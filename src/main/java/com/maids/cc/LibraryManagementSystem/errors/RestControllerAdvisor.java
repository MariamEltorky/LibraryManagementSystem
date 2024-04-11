package com.maids.cc.LibraryManagementSystem.errors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**************************************************************************

 It's a helpful Exception handler class, To show global Exceptions Clearly

 **************************************************************************/
@ControllerAdvice
public class RestControllerAdvisor extends ResponseEntityExceptionHandler {

    static Logger logger = LogManager.getLogger(RestControllerAdvisor.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("RestControllerAdvisor - handleMethodArgumentNotValid - IOException - {}", ex.getMessage());
        List<Object> list = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s : %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(list.toString());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {

//        ex.printStackTrace();

        String[] errors = ex.getCause() == null ? new String[]{ex.getLocalizedMessage()} : new String[]{ex.getCause().getMessage()};
        String error = "";
        for (String s : errors) {
            error += s;
        }
        logger.error("globalExceptionHandler - {}", ex);
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                error,
                request.getDescription(false));
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
