package com.example.phonenosdb.exceptions;

import com.example.phonenosdb.scopes.CustomRequestScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(CustomRestExceptionHandler.class);

    @Autowired
    CustomRequestScope scope;


    @ExceptionHandler({ Exception.class })
    public ResponseEntity handleAll(Exception ex, WebRequest request) {
        logger.error("{} [Exception.InternalServerError] Exception: {}",
                scope.getLogPrefix(), ex.toString(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }

}
