package com.albionrmtempire.exception;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<ProblemDetail> handleGeneralException(Exception ex) {
        return handleResponse(ex, "There was a problem try again later", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ProblemDetail> handleResponse(Exception ex, String customMessage, HttpStatusCode statusCode) {
        log.warn("Exception occurred during request processing: {} statusCode: {}", ex.getMessage(), statusCode.value());
        if (StringUtils.isEmpty(customMessage)) {
            customMessage = ex.getMessage();
        }

        return new ResponseEntity<>(ProblemDetail.forStatusAndDetail(statusCode, customMessage), statusCode);
    }
}
