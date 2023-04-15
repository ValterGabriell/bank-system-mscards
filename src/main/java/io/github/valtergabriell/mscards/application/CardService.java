package io.github.valtergabriell.mscards.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.valtergabriell.mscards.application.domain.AccountCard;
import io.github.valtergabriell.mscards.application.domain.Card;
import io.github.valtergabriell.mscards.application.domain.ProductsBuyed;
import io.github.valtergabriell.mscards.application.domain.dto.*;
import io.github.valtergabriell.mscards.infra.queue.received.RandomValuesCreation;
import io.github.valtergabriell.mscards.infra.queue.send.EmitShop;
import io.github.valtergabriell.mscards.infra.repository.AccountCardRepository;
import io.github.valtergabriell.mscards.infra.repository.CardRepository;
import io.github.valtergabriell.mscards.infra.repository.ProductBuyedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService extends RandomValuesCreation {
    private final CardRepository cardRepository;
    private final AccountCardRepository accountCardRepository;
    private final ProductBuyedRepository productBuyedRepository;

    private final EmitShop emitShop;

    public Card saveCard(RequestCardData cardData) {


        Card card = new Card();
        card.setCardId(UUID.randomUUID().toString());
        card.setCardNumber(generateCardNumber(cardRepository));
        card.setCardLimit(cardData.getCardLimit());
        card.setCardSecurityNumber(generateSecurityNumber(cardRepository));
        card.setExpireDate(LocalDate.now().plusYears(2));
        cardRepository.save(card);
        log.info("card salvo " + card);
        return card;
    }

    public void saveAccountCard(Card card, RequestCardData cardData) {
        AccountCard accountCard = new AccountCard();
        accountCard.setCardLimit(card.getCardLimit());
        accountCard.setCpf(cardData.getCpf());
        accountCard.setCard(card);
        accountCard.setCurrentLimit(card.getCardLimit());
        accountCardRepository.save(accountCard);
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

    public _BuyResponse buySomething(String cpf, BuyRequest buyRequest) throws JsonProcessingException {
        buyRequest.setCpf(cpf);

        //todo: verificar se a conta tem limite para compra
        emitShop.requestCard(buyRequest);
        _BuyResponse buyResponse = new _BuyResponse();
        buyResponse.setMessage("produto aprovado");
        buyResponse.setProduct(buyResponse.getProduct());
        return buyResponse;
    }

    public CommonResponse<ProductsBuyed> getProductBuyedByProductId(String productId) throws ErroResponse {
        Optional<ProductsBuyed> productsBuyed = productBuyedRepository.findById(productId);
        if (productsBuyed.isPresent()) {
            CommonResponse<ProductsBuyed> commonResponse = new CommonResponse<>();
            commonResponse.setMessage("Tudo certo!");
            commonResponse.setData(productsBuyed.get());
            return commonResponse;
        } else {
            throw new ErroResponse("Produto não encontrado");
        }
    }

    public PayInvoiceResponse payInvoice(String productId, PayInvoiceRequest payInvoiceRequest) {
        Optional<ProductsBuyed> optionalProductsBuyed = productBuyedRepository.findById(productId);
        PayInvoiceResponse payInvoiceResponse = new PayInvoiceResponse();
        if (optionalProductsBuyed.isPresent()) {
            ProductsBuyed productsBuyed = optionalProductsBuyed.get();
            BigDecimal installmentsValue = productsBuyed.getInstallmentsValue();
            int numberOfInstallmentToPay = payInvoiceRequest.getNumberOfInstallment();
            BigDecimal totalValueToPay = installmentsValue.multiply(BigDecimal.valueOf(numberOfInstallmentToPay));

            if (payInvoiceRequest.getPaymentValue().doubleValue() == totalValueToPay.doubleValue()) {
                updateNumberOfInstallment(payInvoiceResponse, productsBuyed, numberOfInstallmentToPay);
                BigDecimal currentAccountLimit = productsBuyed.getAccountCard().getCurrentLimit();
                BigDecimal newCurrentAccountLimit = currentAccountLimit.add(totalValueToPay);
                productsBuyed.getAccountCard().setCurrentLimit(newCurrentAccountLimit);
                accountCardRepository.save(productsBuyed.getAccountCard());
            } else {
                payInvoiceResponse.setMessage("Valor a ser pago " + totalValueToPay + "! Você está pagando " + payInvoiceRequest.getPaymentValue());
            }

        } else {
            payInvoiceResponse.setMessage("Produto não encontrado");
        }
        return payInvoiceResponse;
    }

    private void updateNumberOfInstallment(PayInvoiceResponse payInvoiceResponse, ProductsBuyed productsBuyed, int numberOfInstallmentToPay) {
        int newNumberOfInstallment = productsBuyed.getNumberOfInstallments() - numberOfInstallmentToPay;
        productsBuyed.setNumberOfInstallments(newNumberOfInstallment);
        payInvoiceResponse.setMessage("parcela paga!");
    }
}
