package br.com.home.api.exception;

import java.io.Serial;

public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2971571736166929498L;

    public NotFoundException(String message){
        super(message);
    }
}
