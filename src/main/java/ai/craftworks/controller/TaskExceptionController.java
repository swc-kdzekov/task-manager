package ai.craftworks.controller;

import ai.craftworks.exceptions.DateFormatException;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskExceptionController {
    private final Logger logger = (Logger) LogManager.getLogger(this.getClass());

    @ExceptionHandler(value = DateFormatException.class)
    public ResponseEntity<Object> exception(DateFormatException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
