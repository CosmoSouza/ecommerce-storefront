package br.com.dio.storefront.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductInfoDTO(
        UUID id,
        String name,
        BigDecimal price
) {
}
