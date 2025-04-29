package pl.empik.complaint_rest_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(@NotBlank String productName, @NotNull BigDecimal price) {
}
