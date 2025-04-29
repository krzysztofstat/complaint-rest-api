package pl.empik.complaint_rest_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.empik.complaint_rest_api.dto.request.CreateProductRequest;
import pl.empik.complaint_rest_api.dto.response.ApiResponse;
import pl.empik.complaint_rest_api.dto.response.CreateProductResponse;
import pl.empik.complaint_rest_api.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateProductResponse>> createProduct(@RequestBody @Valid CreateProductRequest createProductRequest) {
        CreateProductResponse createdProduct = productService.createProduct(createProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createdProduct));
    }
}
