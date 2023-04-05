package io.github.valtergabriell.mscards.infra.queue;

import java.util.Random;

public class RandomValuesCreation {

    public String createStringWithRandomNumbers(int digits) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            var random = new Random().nextInt(10);
            var numberIntoString = String.valueOf(random);
            stringBuilder.append(numberIntoString);
        }
        return stringBuilder.toString();
    }


}
