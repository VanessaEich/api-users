package net.vanessa.users.rest.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * This class manages application errors
 * @author Vanessa Eich on 03/01/2023
 */
public class ApiErrors {

    @Getter
    private List<String> errors;

    public ApiErrors(List<String> errors){
        this.errors = errors;
    }

    public ApiErrors(String message){
        this.errors = Arrays.asList(message);
    }
}
