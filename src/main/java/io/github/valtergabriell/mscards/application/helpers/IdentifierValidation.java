package io.github.valtergabriell.mscards.application.helpers;

import io.github.valtergabriell.mscards.excpetions.RequestException;

import static io.github.valtergabriell.mscards.excpetions.ExceptionsValues.CPF_LENGHT_INVALID;
import static io.github.valtergabriell.mscards.excpetions.ExceptionsValues.IDENTIFIER_MUST_CONTAIN_ONLY_NUMBERS;

public class IdentifierValidation {

    public boolean isIdLenghtEqual11or14(String id) {
        boolean isIdLenghtOk = id.length() == 11 || id.length() == 14;
        if (!isIdLenghtOk) {
            throw new RequestException(CPF_LENGHT_INVALID);
        }
        return true;
    }

    public boolean isIdentififerContainsOnlyNumbers(String id){
        String regex = "^[0-9]+$";
        if (!id.matches(regex)){
            throw new RequestException(IDENTIFIER_MUST_CONTAIN_ONLY_NUMBERS);
        }
        return true;
    }
}
