package io.github.valtergabriell.mscards.excpetions;


import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiException(String message, HttpStatus status, ZonedDateTime time){}
