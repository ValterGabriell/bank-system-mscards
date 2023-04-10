package io.github.valtergabriell.mscards.application.domain.dto;


import lombok.Data;

@Data
public class CommonResponse<T> {

    private T data;
    private String message;
    private String headerLocation;


}
