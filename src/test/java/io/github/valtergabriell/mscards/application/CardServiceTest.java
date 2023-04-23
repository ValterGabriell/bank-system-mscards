package io.github.valtergabriell.mscards.application;

import io.github.valtergabriell.mscards.application.domain.dto.BuyRequest;
import io.github.valtergabriell.mscards.application.domain.dto.RequestCardDataMock;
import io.github.valtergabriell.mscards.excpetions.RequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardServiceTest {

    private RequestCardDataMock requestCardData;
    private BuyRequest buyRequest;

    @BeforeEach
    void setup() {
        requestCardData = new RequestCardDataMock();
        requestCardData.setCpf("00021645400");
        buyRequest = new BuyRequest();
        buyRequest.setProductValue(BigDecimal.valueOf(3500));
    }

    @Test
    @DisplayName(" it should return true when cpf have 11 chars")
    public void itShouldReturnTrueWhenCpfHave11Chars() {
        int cpf = requestCardData.getCpf().length();
        Assertions.assertEquals(11, cpf);
    }

    @Test
    @DisplayName(" it should throw RequestException when cpf have not 11 chars")
    public void itShouldThrowRequestExceptioneWhenCpfHaveNot11Chars() {
        int cpf = requestCardData.getCpf().length();
        if (cpf != 11) {
            Assertions.assertThrows(RequestException.class, () -> requestCardData.isCpfLenghtEqual11());
        }
    }

    @Test
    @DisplayName("cpf must contains only numbers and return false if is not")
    public void itShouldReturnTrueWhenCpfHaveOnlyNumbers() {
        String regex = "^[0-9]+$";
        String cpf = requestCardData.getCpf();
        boolean matches = cpf.matches(regex);
        Assertions.assertTrue(matches);
    }

    @Test
    @DisplayName("product value should be bigger than zero")
    public void itShouldReturnTrueWhenProductValueBiggerThanZero() {
        BigDecimal productValue = buyRequest.getProductValue();
        int i = productValue.intValue();
        Assertions.assertTrue(i > 0);
    }

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