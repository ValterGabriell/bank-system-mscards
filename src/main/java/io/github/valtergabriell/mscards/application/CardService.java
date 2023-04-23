package io.github.valtergabriell.mscards.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.valtergabriell.mscards.application.domain.AccountCard;
import io.github.valtergabriell.mscards.application.domain.Card;
import io.github.valtergabriell.mscards.application.domain.ProductsBuyed;
import io.github.valtergabriell.mscards.application.domain.dto.*;
import io.github.valtergabriell.mscards.application.helpers.CpfValidation;
import io.github.valtergabriell.mscards.application.helpers.CreateRandomNumbersToCard;
import io.github.valtergabriell.mscards.excpetions.RequestException;
import io.github.valtergabriell.mscards.infra.queue.send.EmitShop;
import io.github.valtergabriell.mscards.infra.repository.AccountCardRepository;
import io.github.valtergabriell.mscards.infra.repository.CardRepository;
import io.github.valtergabriell.mscards.infra.repository.RequestDeleteFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static io.github.valtergabriell.mscards.excpetions.ExceptionsValues.CREDIT_INVALID;
import static io.github.valtergabriell.mscards.excpetions.ExceptionsValues.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService extends CreateRandomNumbersToCard {
    private final CardRepository cardRepository;
    private final AccountCardRepository accountCardRepository;
    private final RequestDeleteFeignClient requestDelete;
    private final EmitShop emitShop;

    public Card saveCard(RequestCardData cardData) {
        CpfValidation cpfValidation = new CpfValidation();
        Card card = new Card();
        if (cpfValidation.isCpfLenghtEqual11(cardData.getCpf())
                && cpfValidation.isCpfContainsOnlyNumbers(cardData.getCpf())) {
            card.setCardId(UUID.randomUUID().toString());
            card.setCardNumber(generateCardNumber(cardRepository));
            card.setCardLimit(cardData.getCardLimit());
            card.setCardSecurityNumber(generateSecurityNumber(cardRepository));
            card.setExpireDate(LocalDate.now().plusYears(2));
            cardRepository.save(card);
            //savind account card
            saveAccoundCard(card, cardData);
        }
        return card;
    }

    private void saveAccoundCard(Card card, RequestCardData cardData) {
        AccountCard accountCard = new AccountCard();
        accountCard.setCardLimit(card.getCardLimit());
        accountCard.setCpf(cardData.getCpf());
        accountCard.setCard(card);
        accountCard.setCurrentLimit(card.getCardLimit());
        accountCardRepository.save(accountCard);
        log.info("Conta de cartao salva - cpf da conta: " + accountCard.getCpf());
    }

    public void updateCurrentLimitAfterBuy(AccountCard accountCard) {
        CpfValidation cpfValidation = new CpfValidation();
        if (cpfValidation.isCpfLenghtEqual11(accountCard.getCpf()) && cpfValidation.isCpfContainsOnlyNumbers(accountCard.getCpf())) {
            AccountCard currentAccountCard = accountCardRepository.findByCpf(accountCard.getCpf());
            if (currentAccountCard == null) {
                throw new RequestException(USER_NOT_FOUND);
            }
            currentAccountCard.setCurrentLimit(accountCard.getCurrentLimit());
            accountCardRepository.save(currentAccountCard);
        }
    }

    public CommonResponse<AccountCard> getAccountCardByClientCpf(String cpf) {
        CpfValidation cpfValidation = new CpfValidation();
        CommonResponse<AccountCard> commonResponse = null;
        if (cpfValidation.isCpfLenghtEqual11(cpf) && cpfValidation.isCpfContainsOnlyNumbers(cpf)) {
            AccountCard accountCard = accountCardRepository.findByCpf(cpf);
            if (accountCard != null) {
                commonResponse = new CommonResponse<>();
                commonResponse.setData(accountCard);
                commonResponse.setMessage("Tudo certo!");
            } else {
                throw new RequestException(USER_NOT_FOUND);
            }
        }
        return commonResponse;
    }

    private String generateCardNumber(CardRepository cardRepository) {
        return generateRandomValueToCard(cardRepository, 13);
    }

    private String generateSecurityNumber(CardRepository cardRepository) {
        return generateRandomValueToCard(cardRepository, 3);
    }

    public BuyResponse buySomething(String cpf, BuyRequest buyRequest) throws JsonProcessingException {
        CpfValidation cpfValidation = new CpfValidation();
        BuyResponse buyResponse = null;
        if (cpfValidation.isCpfLenghtEqual11(cpf)
                && cpfValidation.isCpfContainsOnlyNumbers(cpf)
                && buyRequest.isProductValueBiggerThanZero()) {

            buyRequest.setCpf(cpf);
            AccountCard accountCard = accountCardRepository.findByCpf(cpf);
            if (accountCard == null) {
                throw new RequestException(USER_NOT_FOUND);
            }
            buyResponse = new BuyResponse();
            buyRequest.setAccountCard(accountCard.toDto());
            BigDecimal currentAccountCardLimit = accountCard.getCurrentLimit();
            if (currentAccountCardLimit.intValue() >= buyRequest.getProductValue().intValue()) {
                accountCardRepository.save(accountCard);
                buyResponse.setMessage("produto aprovado");
                buyResponse.setProtocol(UUID.randomUUID().toString());
                buyResponse.setProduct(buyRequest.getProduct());
                buyRequest.setProtocol(buyResponse.getProtocol());
                emitShop.requestPurchase(buyRequest);
            } else {
                throw new RequestException(CREDIT_INVALID);
            }
        }
        return buyResponse;
    }

    public void deleteCardAccount(String cpf) {
        //todo: verificar se o usuario possui pendencias de conta
        CpfValidation cpfValidation = new CpfValidation();
        if (cpfValidation.isCpfLenghtEqual11(cpf) && cpfValidation.isCpfContainsOnlyNumbers(cpf)) {
            AccountCard accountCard = accountCardRepository.findByCpf(cpf);
            if (accountCard == null) {
                throw new RequestException(USER_NOT_FOUND);
            }
            accountCardRepository.delete(accountCard);
            requestDelete.deleteAccountData(cpf);
        }
    }

    private void updateNumberOfInstallment(PayInvoiceResponse payInvoiceResponse, ProductsBuyed productsBuyed, int numberOfInstallmentToPay) {
        int newNumberOfInstallment = productsBuyed.getNumberOfInstallments() - numberOfInstallmentToPay;
        productsBuyed.setNumberOfInstallments(newNumberOfInstallment);
        payInvoiceResponse.setMessage("parcela paga!");
    }
}
