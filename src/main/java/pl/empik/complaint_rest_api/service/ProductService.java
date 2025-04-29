package pl.empik.complaint_rest_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.empik.complaint_rest_api.builder.ProductBuilder;
import pl.empik.complaint_rest_api.dto.request.CreateProductRequest;
import pl.empik.complaint_rest_api.dto.response.CreateProductResponse;
import pl.empik.complaint_rest_api.exception.ProductNotFoundException;
import pl.empik.complaint_rest_api.model.Product;
import pl.empik.complaint_rest_api.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Product findProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public CreateProductResponse createProduct(CreateProductRequest createProductRequest) {
        return productRepository.findByProductNameAndPrice(createProductRequest.productName(), createProductRequest.price())
                .map(CreateProductResponse::fromProduct)
                .orElseGet(() -> createNewProduct(createProductRequest));
    }

    private CreateProductResponse createNewProduct(CreateProductRequest createProductRequest) {
        Product product = ProductBuilder.buildProduct(createProductRequest);
        productRepository.save(product);

        return CreateProductResponse.fromProduct(product);
    }
}
