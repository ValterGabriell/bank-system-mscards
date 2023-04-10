package io.github.valtergabriell.mscards.application.domain;

import io.github.valtergabriell.mscards.application.CardService;
import io.github.valtergabriell.mscards.application.domain.dto.BuyAprooved;
import io.github.valtergabriell.mscards.application.domain.dto.BuyRequest;
import io.github.valtergabriell.mscards.application.domain.dto.CommonResponse;
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


    @PostMapping(value = "buy", params = {"cpf"})
    public ResponseEntity<BuyAprooved> buySomething(@RequestParam String cpf, @RequestBody BuyRequest buyRequest) {
        cardService.buySomething(cpf, buyRequest);
        return new ResponseEntity<>(new BuyAprooved(), HttpStatus.OK);
    }


}
