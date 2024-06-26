package com.albionrmtempire.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException ofItem(String name) {
        return new NotFoundException("Requested item %s does not exist".formatted(name));
    }

    public static NotFoundException ofResource(String name) {
        return new NotFoundException("Requested resource %s does not exist".formatted(name));
    }
}
