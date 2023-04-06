package io.github.valtergabriell.mscards.application.domain;

import io.github.valtergabriell.mscards.application.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;


    @GetMapping(params = {"cpf"})
    public ResponseEntity getAccountCardByCpf(@RequestParam String cpf) {
        AccountCard client = cardService.getAccountCardByClientCpf(cpf);
        return ResponseEntity.ok(client);
    }

    @GetMapping()
    public ResponseEntity getAllAccountsCard() {
        List<AccountCard> client = cardService.getAllAccountCard();
        return ResponseEntity.ok(client);
    }


}
