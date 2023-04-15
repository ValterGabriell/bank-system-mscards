package io.github.valtergabriell.mscards.application.domain.dto;

public class ErroResponse extends Exception{
    public ErroResponse(String message) {
        super(message);
    }
}
