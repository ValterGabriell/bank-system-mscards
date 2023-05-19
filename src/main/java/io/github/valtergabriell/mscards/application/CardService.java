package io.github.valtergabriell.mscards.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.valtergabriell.mscards.application.domain.AccountCard;
import io.github.valtergabriell.mscards.application.domain.Card;
import io.github.valtergabriell.mscards.application.domain.ProductsBuyed;
import io.github.valtergabriell.mscards.application.domain.dto.*;
import io.github.valtergabriell.mscards.application.helpers.CreateRandomNumbersToCard;
import io.github.valtergabriell.mscards.application.helpers.IdentifierValidation;
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
        IdentifierValidation identifierValidation = new IdentifierValidation();
        Card card = new Card();
        if (identifierValidation.isIdLenghtEqual11or14(cardData.getIdentifier())
                && identifierValidation.isIdentififerContainsOnlyNumbers(cardData.getIdentifier())) {
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
        accountCard.setIdentifier(cardData.getIdentifier());
        accountCard.setCard(card);
        accountCard.setCurrentLimit(card.getCardLimit());
        accountCardRepository.save(accountCard);
        log.info("Conta de cartao salva - id da conta: " + accountCard.getIdentifier());
    }

    public void updateCurrentLimitAfterBuy(AccountCard accountCard) {
        IdentifierValidation identifierValidation = new IdentifierValidation();
        if (identifierValidation.isIdLenghtEqual11or14(accountCard.getIdentifier()) && identifierValidation.isIdentififerContainsOnlyNumbers(accountCard.getIdentifier())) {
            AccountCard currentAccountCard = accountCardRepository.findByIdentifier(accountCard.getIdentifier());
            if (currentAccountCard == null) {
                throw new RequestException(USER_NOT_FOUND);
            }
            currentAccountCard.setCurrentLimit(accountCard.getCurrentLimit());
            accountCardRepository.save(currentAccountCard);
        }
    }

    public CommonResponse<AccountCard> getAccountCardByClientIdentifier(String id) {
        IdentifierValidation identifierValidation = new IdentifierValidation();
        CommonResponse<AccountCard> commonResponse = null;
        if (identifierValidation.isIdLenghtEqual11or14(id) && identifierValidation.isIdentififerContainsOnlyNumbers(id)) {
            AccountCard accountCard = accountCardRepository.findByIdentifier(id);
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

    private boolean isProductValueBiggerThanZero(BigDecimal productValue) {
        int i = productValue.intValue();
        return i > 0;
    }

    public BuyResponse buySomething(String id, BuyRequest buyRequest) throws JsonProcessingException {
        IdentifierValidation identifierValidation = new IdentifierValidation();
        BuyResponse buyResponse = null;
        if (identifierValidation.isIdLenghtEqual11or14(id)
                && identifierValidation.isIdentififerContainsOnlyNumbers(id)
                && isProductValueBiggerThanZero(buyRequest.getProductValue())) {

            buyRequest.setIdentifier(id);
            AccountCard accountCard = accountCardRepository.findByIdentifier(id);
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

    public void deleteCardAccount(String id) {
        //todo: verificar se o usuario possui pendencias de conta
        IdentifierValidation identifierValidation = new IdentifierValidation();
        if (identifierValidation.isIdLenghtEqual11or14(id) && identifierValidation.isIdentififerContainsOnlyNumbers(id)) {
            AccountCard accountCard = accountCardRepository.findByIdentifier(id);
            if (accountCard == null) {
                throw new RequestException(USER_NOT_FOUND);
            }
            accountCardRepository.delete(accountCard);
            requestDelete.deleteAccountData(id);
        }
    }

    private void updateNumberOfInstallment(PayInvoiceResponse payInvoiceResponse, ProductsBuyed productsBuyed, int numberOfInstallmentToPay) {
        int newNumberOfInstallment = productsBuyed.getNumberOfInstallments() - numberOfInstallmentToPay;
        productsBuyed.setNumberOfInstallments(newNumberOfInstallment);
        payInvoiceResponse.setMessage("parcela paga!");
    }

    public void deleteCardByPersonIdentifier(String value) {
        AccountCard accountCard = accountCardRepository.findByIdentifier(value);
        Card card = accountCard.getCard();
        accountCardRepository.delete(accountCard);
        cardRepository.delete(card);
    }
}
