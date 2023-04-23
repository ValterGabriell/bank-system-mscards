package io.github.valtergabriell.mscards.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.valtergabriell.mscards.application.domain.AccountCard;
import io.github.valtergabriell.mscards.application.domain.Card;
import io.github.valtergabriell.mscards.application.domain.ProductsBuyed;
import io.github.valtergabriell.mscards.application.domain.dto.*;
import io.github.valtergabriell.mscards.infra.queue.send.EmitShop;
import io.github.valtergabriell.mscards.infra.repository.AccountCardRepository;
import io.github.valtergabriell.mscards.infra.repository.CardRepository;
import io.github.valtergabriell.mscards.infra.repository.RequestDelete;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService extends RandomValuesCreation {
    private final CardRepository cardRepository;
    private final AccountCardRepository accountCardRepository;
    private final RequestDelete requestDelete;
    private final EmitShop emitShop;

    public Card saveCard(RequestCardData cardData) {
        Card card = new Card();
        if (cardData.isCpfLenghtEqual11()){
            card.setCardId(UUID.randomUUID().toString());
            card.setCardNumber(generateCardNumber(cardRepository));
            card.setCardLimit(cardData.getCardLimit());
            card.setCardSecurityNumber(generateSecurityNumber(cardRepository));
            card.setExpireDate(LocalDate.now().plusYears(2));
            cardRepository.save(card);
        }
        return card;
    }

    public void saveAccoundCard(Card card, RequestCardData cardData) {
        AccountCard accountCard = new AccountCard();
        accountCard.setCardLimit(card.getCardLimit());
        accountCard.setCpf(cardData.getCpf());
        accountCard.setCard(card);
        accountCard.setCurrentLimit(card.getCardLimit());
        accountCardRepository.save(accountCard);
        log.info("Conta de cartao salva - cpf da conta: " + accountCard.getCpf());
    }

    public void updateCurrentLimitAfterBuy(AccountCard accountCard) {
        AccountCard currentAccountCard = accountCardRepository.findByCpf(accountCard.getCpf());
        currentAccountCard.setCurrentLimit(accountCard.getCurrentLimit());
        accountCardRepository.save(currentAccountCard);
        log.info("Limite da conta atualizado apos a compra: " + accountCard.getCurrentLimit());
    }

    public CommonResponse<AccountCard> getAccountCardByClientCpf(String cpf) {
        AccountCard accountCard = accountCardRepository.findByCpf(cpf);
        CommonResponse<AccountCard> commonResponse = new CommonResponse<>();
        if (accountCard != null) {
            commonResponse.setData(accountCard);
            commonResponse.setMessage("Tudo certo!");
        } else {
            commonResponse.setMessage("Falha ao receber o cartao do cleinte");
        }
        return commonResponse;
    }

    private String generateCardNumber(CardRepository cardRepository) {
        return generateRandomValue(cardRepository, 13);
    }

    private String generateSecurityNumber(CardRepository cardRepository) {
        return generateRandomValue(cardRepository, 3);
    }

    public BuyResponse buySomething(String cpf, BuyRequest buyRequest) throws JsonProcessingException {
        buyRequest.setCpf(cpf);
        AccountCard accountCard = accountCardRepository.findByCpf(buyRequest.getCpf());
        BuyResponse buyResponse = new BuyResponse();
        buyRequest.setAccountCard(accountCard.toDto());

        BigDecimal currentAccountCardLimit = accountCard.getCurrentLimit();
        if (currentAccountCardLimit.intValue() >= buyRequest.getBuyValue().intValue()) {
            accountCardRepository.save(accountCard);
            buyResponse.setMessage("produto aprovado");
            buyResponse.setProtocol(UUID.randomUUID().toString());
            buyResponse.setProduct(buyRequest.getProduct());
            buyRequest.setProtocol(buyResponse.getProtocol());
            emitShop.requestCard(buyRequest);
        } else {
            buyResponse.setMessage("produto reprovado por falta de crédito");
        }
        return buyResponse;
    }

    public void deleteCardAccount(String cpf){
        //todo: excecao caso nao exista o cpf; verificar se o usuario possui pendencias de conta
        AccountCard accountCard = accountCardRepository.findByCpf(cpf);
        accountCardRepository.delete(accountCard);
        requestDelete.deleteAccountData(cpf);
    }

    private void updateNumberOfInstallment(PayInvoiceResponse payInvoiceResponse, ProductsBuyed productsBuyed, int numberOfInstallmentToPay) {
        int newNumberOfInstallment = productsBuyed.getNumberOfInstallments() - numberOfInstallmentToPay;
        productsBuyed.setNumberOfInstallments(newNumberOfInstallment);
        payInvoiceResponse.setMessage("parcela paga!");
    }
}
