package io.github.valtergabriell.mscards.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.valtergabriell.mscards.application.domain.AccountCard;
import io.github.valtergabriell.mscards.application.domain.ProductsBuyed;
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


    @GetMapping(params = {"cpf"})
    public ResponseEntity<CommonResponse<AccountCard>> getAccountCardByCpf(@RequestParam String cpf) {
        CommonResponse<AccountCard> accountCardByClientCpf = cardService.getAccountCardByClientCpf(cpf);
        return new ResponseEntity<>(accountCardByClientCpf, HttpStatus.OK);
    }

//    @GetMapping(params = {"productId"})
//    public ResponseEntity<CommonResponse<ProductsBuyed>> getProductByProductId(@RequestParam String productId) throws ErroResponse {
//        CommonResponse<ProductsBuyed> accountCardByClientCpf = cardService.getProductBuyedByProductId(productId);
//        return new ResponseEntity<>(accountCardByClientCpf, HttpStatus.OK);
//    }


    @PostMapping(value = "buy", params = {"cpf"})
    public ResponseEntity<BuyResponse> buySomething(@RequestParam String cpf, @RequestBody BuyRequest buyRequest) throws JsonProcessingException {
        BuyResponse buyResponse = cardService.buySomething(cpf, buyRequest);
        return new ResponseEntity<>(buyResponse, HttpStatus.OK);
    }

//    @PostMapping(value = "pay", params = {"productId"})
//    public ResponseEntity<PayInvoiceResponse> buySomething(@RequestParam String productId, @RequestBody PayInvoiceRequest invoiceRequest) {
//        PayInvoiceResponse payInvoiceResponse = cardService.payInvoice(productId, invoiceRequest);
//        return new ResponseEntity<>(payInvoiceResponse, HttpStatus.OK);
//    }


}
