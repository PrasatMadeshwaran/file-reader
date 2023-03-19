package com.prasat.filereader.controller;


import com.prasat.filereader.exception.DataNotFoundException;
import com.prasat.filereader.exception.FileTypeNotSupportedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CsvExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error("Exception occurred: ", ex);
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleNoFoundException(DataNotFoundException ex) {
        log.error("Data not found: ", ex);
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileTypeNotSupportedException.class)
    public ResponseEntity<Object> handleUnsupportedFileException(FileTypeNotSupportedException ex) {
        log.error("Unsupported file type exception: ", ex);
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
