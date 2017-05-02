package ru.rodionovsasha.shoppinglist.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorDTO {
    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public void addFieldError(String field, String message) {
        fieldErrors.add(new FieldErrorDTO(field, message));
    }
}