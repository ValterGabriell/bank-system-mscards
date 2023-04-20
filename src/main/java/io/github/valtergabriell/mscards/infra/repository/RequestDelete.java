package io.github.valtergabriell.mscards.infra.repository;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msaccount", path = "/account")
public interface RequestDelete {
    @DeleteMapping(params = "cpf")
    void deleteAccountData(@RequestParam("cpf") String cpf);
}
