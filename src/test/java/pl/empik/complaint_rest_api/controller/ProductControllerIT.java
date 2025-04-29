package pl.empik.complaint_rest_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.empik.complaint_rest_api.BaseIT;
import pl.empik.complaint_rest_api.TestData;
import pl.empik.complaint_rest_api.dto.request.CreateProductRequest;
import pl.empik.complaint_rest_api.dto.response.CreateProductResponse;
import pl.empik.complaint_rest_api.model.Product;
import pl.empik.complaint_rest_api.repository.ComplaintRepository;
import pl.empik.complaint_rest_api.repository.ProductRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT extends BaseIT {

    private static final String CREATE_PRODUCT_URL = "/v1/product";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        complaintRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void shouldCreateProduct() throws Exception {
        //given
        CreateProductRequest createProductRequest = new CreateProductRequest(TestData.PRODUCT_NAME, TestData.PRODUCT_PRICE);

        //when
        MvcResult mvcResult = mockMvc.perform(post(CREATE_PRODUCT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProductRequest)))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn();

        //then
        CreateProductResponse createProductResponse = fromJson(mvcResult, CreateProductResponse.class);

        Optional<Product> product = productRepository.findById(createProductResponse.id());
        assertThat(product.isPresent()).isTrue();
        assertThat(product.get().getProductName()).isEqualTo(TestData.PRODUCT_NAME);
        assertThat(product.get().getPrice()).isEqualTo(TestData.PRODUCT_PRICE);
    }

    @Test
    void shouldReturnExistProduct() throws Exception {
        //given
        Product existProduct = TestData.createProduct(TestData.PRODUCT_ID, TestData.PRODUCT_NAME, TestData.PRODUCT_PRICE);
        productRepository.save(existProduct);

        CreateProductRequest createProductRequest = new CreateProductRequest(TestData.PRODUCT_NAME, TestData.PRODUCT_PRICE);

        //when
        MvcResult mvcResult = mockMvc.perform(post(CREATE_PRODUCT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createProductRequest)))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn();

        //then
        CreateProductResponse createProductResponse = fromJson(mvcResult, CreateProductResponse.class);

        assertThat(createProductResponse.id()).isEqualTo(existProduct.getId());
    }
}
