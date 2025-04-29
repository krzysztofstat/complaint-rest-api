package pl.empik.complaint_rest_api.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.empik.complaint_rest_api.dto.request.CreateProductRequest;
import pl.empik.complaint_rest_api.model.Product;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductBuilder {

    public static Product buildProduct(CreateProductRequest createProductRequest) {
        return Product.builder()
                .productName(createProductRequest.productName())
                .price(createProductRequest.price())
                .build();
    }
}
