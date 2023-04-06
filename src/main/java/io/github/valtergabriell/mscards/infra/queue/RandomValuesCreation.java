package io.github.valtergabriell.mscards.infra.queue;

import io.github.valtergabriell.mscards.infra.repository.CardRepository;

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

    public String generateRandomValue(CardRepository cardRepository, int digit){
        var finalNumber = createStringWithRandomNumbers(digit);
        boolean cardNumberAlreadyExists = cardRepository.findByCardNumber(finalNumber).isPresent();

        if (cardNumberAlreadyExists) {
            generateRandomValue(cardRepository, digit);
        }

        return finalNumber;
    }


}
