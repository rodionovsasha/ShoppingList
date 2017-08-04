package ru.rodionovsasha.shoppinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class FieldErrorDTO {
    private String field;
    private String message;
}
