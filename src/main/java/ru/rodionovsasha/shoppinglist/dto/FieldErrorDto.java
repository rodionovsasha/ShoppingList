package ru.rodionovsasha.shoppinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class FieldErrorDto {
    private String field;
    private String message;
}
