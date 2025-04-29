package pl.empik.complaint_rest_api.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.empik.complaint_rest_api.dto.response.ApiResponse;
import pl.empik.complaint_rest_api.exception.ComplaintNotFoundException;
import pl.empik.complaint_rest_api.exception.ProductNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            ComplaintNotFoundException.class,
            ProductNotFoundException.class
    })
    public ResponseEntity<ApiResponse<String>> handleNotFoundException(Exception e) {
        ApiResponse<String> apiResponse = ApiResponse.error(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiResponse<String> response = ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                toErrorMessage(e.getFieldError()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception e) {
        ApiResponse<String> exceptionResponse = ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    private String toErrorMessage(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}