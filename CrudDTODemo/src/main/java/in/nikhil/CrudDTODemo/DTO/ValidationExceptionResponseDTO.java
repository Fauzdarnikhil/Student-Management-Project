package in.nikhil.CrudDTODemo.DTO;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationExceptionResponseDTO {
    private LocalDateTime timeStamp;
    private int StatusCode;
    private String error;
    private String message;
    private String path;
    private Map<String,String> errors;

    public ValidationExceptionResponseDTO(LocalDateTime timeStamp, int statusCode, String error, String message, String path, Map<String, String> errors) {
        this.timeStamp = timeStamp;
        StatusCode = statusCode;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
