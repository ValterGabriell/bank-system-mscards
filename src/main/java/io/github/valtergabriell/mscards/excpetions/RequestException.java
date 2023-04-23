package io.github.valtergabriell.mscards.excpetions;

public class RequestException extends RuntimeException{
    public RequestException(String message) {
        super(message);
    }
}
