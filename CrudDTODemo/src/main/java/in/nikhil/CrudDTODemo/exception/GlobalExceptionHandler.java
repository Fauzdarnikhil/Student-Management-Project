package in.nikhil.CrudDTODemo.exception;

import in.nikhil.CrudDTODemo.DTO.ExceptionResponseDTO;
import in.nikhil.CrudDTODemo.DTO.ValidationExceptionResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice   // -> IT is the helper class of all controller class . if we want to provide any common behaviour then we can write that in this class with RestControllerAdvice annotation.

public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ExceptionResponseDTO> handleDuplicateResourceException(DuplicateResourceException e, HttpServletRequest request){

        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){

        Map<String,String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors()
                .forEach((error)->
                            errors.put(error.getField(),error.getDefaultMessage()));

        ValidationExceptionResponseDTO exceptionResponse = new ValidationExceptionResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation Failed",
                request.getRequestURI(),
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request){

        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseDTO> handleRuntimeException(RuntimeException e, HttpServletRequest request){
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(RuntimeException e,HttpServletRequest request){
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong. Try again later");
    }
}
