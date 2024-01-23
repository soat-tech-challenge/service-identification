package br.com.grupo63.serviceidentification.api.controller;

import br.com.grupo63.serviceidentification.api.controller.dto.DefaultResponseDTO;
import br.com.grupo63.serviceidentification.exception.GenericException;
import br.com.grupo63.serviceidentification.exception.NotFoundException;
import br.com.grupo63.serviceidentification.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractAPIController {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler
    public ResponseEntity<DefaultResponseDTO> handleException(Exception exception) {
        exception.printStackTrace();
        DefaultResponseDTO responseDTO = new DefaultResponseDTO(
                messageSource.getMessage("default.title.unknownError", null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("default.title.unknownError.description", null, LocaleContextHolder.getLocale()));

        if (exception instanceof ValidationException validationException) {
            responseDTO.setTitle(messageSource.getMessage(validationException.getName(), null, LocaleContextHolder.getLocale()));
            responseDTO.setDescription(messageSource.getMessage(validationException.getDescription(), null, LocaleContextHolder.getLocale()));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        } else if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            responseDTO.setTitle(messageSource.getMessage("default.title.validationError", null, LocaleContextHolder.getLocale()));
            responseDTO.setDescription(
                    methodArgumentNotValidException
                            .getBindingResult().getAllErrors()
                            .stream().map(ObjectError::getDefaultMessage)
                            .filter(Objects::nonNull)
                            .map(message -> messageSource.getMessage(message, null, LocaleContextHolder.getLocale()))
                            .collect(Collectors.joining("; ")));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        } else if (exception instanceof NotFoundException) {
            responseDTO.setTitle(messageSource.getMessage("default.title.notFoundError", null, LocaleContextHolder.getLocale()));
            responseDTO.setDescription(messageSource.getMessage("default.title.notFoundError.description", null, LocaleContextHolder.getLocale()));

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        } else if (exception instanceof GenericException genericException) {
            responseDTO.setTitle(genericException.getName());
            responseDTO.setDescription(genericException.getDescription());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }

        return ResponseEntity.internalServerError().body(responseDTO);
    }
}
