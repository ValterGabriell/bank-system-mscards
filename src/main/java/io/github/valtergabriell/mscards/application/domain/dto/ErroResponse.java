package io.github.valtergabriell.mscards.application.domain.dto;

public class ErroResponse extends RuntimeException {
    public ErroResponse(String message) {
        super(message);
    }
}
