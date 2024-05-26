package com.albionrmtempire.exception;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<ProblemDetail> handleGeneralException(Exception ex) {
        log.error(ExceptionUtils.getStackTrace(ex));
        return handleResponse(ex, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException ex) {
        return handleResponse(ex, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    ResponseEntity<ProblemDetail> handleMalformedRequestException(MalformedOrderRequestException ex) {
        return handleResponse(ex, ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<ProblemDetail> handleResponse(Exception ex, String customMessage, HttpStatusCode statusCode) {
        log.warn("Exception occurred during request processing: {} statusCode: {}", ex.getMessage(), statusCode.value());
        if (StringUtils.isEmpty(customMessage)) {
            customMessage = "There was a problem try again later";
        }

        return new ResponseEntity<>(ProblemDetail.forStatusAndDetail(statusCode, customMessage), statusCode);
    }
}
