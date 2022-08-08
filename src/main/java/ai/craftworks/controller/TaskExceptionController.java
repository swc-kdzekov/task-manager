package ai.craftworks.controller;

import ai.craftworks.exceptions.DateFormatException;
import ai.craftworks.exceptions.NoTaskFoundException;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskExceptionController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = DateFormatException.class)
    public ResponseEntity<Object> exception(DateFormatException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoTaskFoundException.class)
    public ResponseEntity<Object> exception(NoTaskFoundException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
