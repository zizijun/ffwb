package com.cxtx.exception;

/**
 * Created by jinchuyang on 16/10/19.
 */

import com.cxtx.model.ServiceResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleCustomerInfoWebExceptions(Exception ex,
                                                                     WebRequest request) {

        HttpStatus status;
        HttpHeaders headers = new HttpHeaders();
        String message = "";

        ObjectMapper objectMapper = new ObjectMapper();

        StringBuilder sb = new StringBuilder(request.getContextPath()).append('?');
        if (request.getParameterMap() != null) {
            for (String key : request.getParameterMap().keySet()) {
                sb.append('&').append(key).append('=').append(request.getParameter(key));
            }
        }
        logger.error(sb.toString());

        if (ex instanceof HttpStatusCodeException) {
            status = ((HttpStatusCodeException) ex).getStatusCode();
            message = null;
            logger.error(request.getParameterMap(), ex);
            logger.error(ex.getMessage(), ex);
        } else {
            logger.error(ex.getMessage(), ex);
            status = HttpStatus.OK;

            ServiceResult result = ServiceResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
            headers.add("Content-Type", "application/json;charset=UTF-8");
            try {
                message = objectMapper.writeValueAsString(result);
            } catch (IOException e) {
            }
        }

        String bodyOfResponse = message;
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }
}
