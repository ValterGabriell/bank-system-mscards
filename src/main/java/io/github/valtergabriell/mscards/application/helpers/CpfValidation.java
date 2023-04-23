package io.github.valtergabriell.mscards.application.helpers;

import io.github.valtergabriell.mscards.excpetions.RequestException;

import static io.github.valtergabriell.mscards.excpetions.ExceptionsValues.CPF_LENGHT_INVALID;
import static io.github.valtergabriell.mscards.excpetions.ExceptionsValues.CPF_MUST_CONTAIN_ONLY_NUMBERS;

public class CpfValidation {

    public boolean isCpfLenghtEqual11(String cpf) {
        boolean isCpfLenghtOk = cpf.length() == 11;
        if (!isCpfLenghtOk) {
            throw new RequestException(CPF_LENGHT_INVALID);
        }
        return true;
    }

    public boolean isCpfContainsOnlyNumbers(String cpf){
        String regex = "^[0-9]+$";
        if (!cpf.matches(regex)){
            throw new RequestException(CPF_MUST_CONTAIN_ONLY_NUMBERS);
        }
        return true;
    }
}
