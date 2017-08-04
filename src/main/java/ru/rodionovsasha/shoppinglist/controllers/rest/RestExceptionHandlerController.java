package ru.rodionovsasha.shoppinglist.controllers.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.rodionovsasha.shoppinglist.dto.ValidationErrorDTO;

import java.util.List;

@Slf4j
@ControllerAdvice
public class RestExceptionHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException exception) {
        return processFieldErrors(exception.getBindingResult().getFieldErrors());
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO();
        log.error("Validation errors:");
        fieldErrors.forEach(fieldError -> {
            log.error("Field '" + fieldError.getField() + "': " + fieldError.getDefaultMessage());
            validationErrorDTO.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return validationErrorDTO;
    }
}
