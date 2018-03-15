package ru.rodionovsasha.shoppinglist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public final class NotFoundException extends RuntimeException {
    private NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException forId(long id) {
        return new NotFoundException(String.format("The entity with id '%d' could not be found", id));
    }
}
