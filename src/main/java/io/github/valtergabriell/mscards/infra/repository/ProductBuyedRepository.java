package io.github.valtergabriell.mscards.infra.repository;

import io.github.valtergabriell.mscards.application.domain.ProductsBuyed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBuyedRepository extends JpaRepository<ProductsBuyed, String> {
}
