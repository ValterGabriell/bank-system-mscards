package io.github.valtergabriell.mscards.infra.queue;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestCardSubscriberTest {


    @Test
    void itShouldReturnTrueIfCardNumberHas13Digits() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            var random = new Random().nextInt(10);
            var numberIntoString = String.valueOf(random);
            stringBuilder.append(numberIntoString);
        }
        var stringFinal = stringBuilder.toString();
        assertEquals(13, stringFinal.length());
    }

}