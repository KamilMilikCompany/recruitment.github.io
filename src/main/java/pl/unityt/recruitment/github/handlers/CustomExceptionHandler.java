package pl.unityt.recruitment.github.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.unityt.recruitment.github.models.exceptions.ErrorResponse;
import pl.unityt.recruitment.github.models.exceptions.RestTemplateException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(RestTemplateException.class)
    ResponseEntity<ErrorResponse> handleRestTemplateException(RestTemplateException ex, HttpServletRequest request) {
        logger.warn(ex.toString());
        return new ResponseEntity<>(new ErrorResponse(ex, request.getRequestURI()), ex.getStatusCode());
    }

}