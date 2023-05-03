package io.github.valtergabriell.mscards.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.valtergabriell.mscards.application.domain.AccountCard;
import io.github.valtergabriell.mscards.application.domain.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;


    @GetMapping(params = {"id"})
    public ResponseEntity<CommonResponse<AccountCard>> getAccountCardByCpf(@RequestParam String id) {
        CommonResponse<AccountCard> accountCardByClientCpf = cardService.getAccountCardByClientIdentifier(id);
        return new ResponseEntity<>(accountCardByClientCpf, HttpStatus.OK);
    }

    @DeleteMapping(params = "id")
    public ResponseEntity deleteAccountData(@RequestParam("id") String id) {
        cardService.deleteCardAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "buy", params = {"id"})
    public ResponseEntity<BuyResponse> buySomething(@RequestParam String id, @RequestBody BuyRequest buyRequest) throws JsonProcessingException {
        BuyResponse buyResponse = cardService.buySomething(id, buyRequest);
        return new ResponseEntity<>(buyResponse, HttpStatus.OK);
    }

//    @PostMapping(value = "pay", params = {"productId"})
//    public ResponseEntity<PayInvoiceResponse> payInvoice(@RequestParam String productId, @RequestBody PayInvoiceRequest invoiceRequest) {
//        PayInvoiceResponse payInvoiceResponse = cardService.payInvoice(productId, invoiceRequest);
//        return new ResponseEntity<>(payInvoiceResponse, HttpStatus.OK);
//    }


}
