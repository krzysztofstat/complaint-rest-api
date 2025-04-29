package pl.empik.complaint_rest_api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import pl.empik.complaint_rest_api.model.Product;

import java.time.LocalDateTime;

@Builder
public record CreateProductResponse(String id,
                                          @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
                                          LocalDateTime createdAt) {

    public static CreateProductResponse fromProduct(Product product) {
        return CreateProductResponse.builder()
                .id(product.getId())
                .createdAt(product.getLifecycle().getCreateDate())
                .build();
    }
}
