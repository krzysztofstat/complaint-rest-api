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
import pl.empik.complaint_rest_api.dto.request.CreateComplaintRequest;
import pl.empik.complaint_rest_api.dto.request.PatchComplaintRequest;
import pl.empik.complaint_rest_api.dto.response.ComplaintListResponse;
import pl.empik.complaint_rest_api.dto.response.ComplaintResponse;
import pl.empik.complaint_rest_api.dto.response.CreateComplaintResponse;
import pl.empik.complaint_rest_api.model.Complaint;
import pl.empik.complaint_rest_api.model.Customer;
import pl.empik.complaint_rest_api.model.Product;
import pl.empik.complaint_rest_api.repository.ComplaintRepository;
import pl.empik.complaint_rest_api.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ComplaintControllerIT extends BaseIT {

    private static final String CREATE_COMPLAINT_URL = "/v1/complaint";
    private static final String GET_COMPLAINT_URL = "/v1/complaint/{id}";
    private static final String GET_PAGED_COMPLAINT_URL = "/v1/complaint";
    private static final String PATCH_COMPLAINT_URL = "/v1/complaint/patch";
    private static final String UPDATED_COMPLAINT_RESPONSE = "Complaint updated";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        complaintRepository.deleteAll();
        productRepository.deleteAll();
    }


    @Test
    void shouldCreateAndSaveComplaint() throws Exception {
        //given
        Product product = TestData.createProduct(TestData.PRODUCT_ID, TestData.PRODUCT_NAME, TestData.PRODUCT_PRICE);
        productRepository.saveAndFlush(product);

        CreateComplaintRequest createComplaintRequest =
                new CreateComplaintRequest(TestData.PRODUCT_ID, TestData.NEW_CUSTOMER_EMAIL, TestData.NEW_COMPLAINT_DESC);

        //when
        MvcResult mvcResult = mockMvc.perform(post(CREATE_COMPLAINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createComplaintRequest)))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn();

        //then
        CreateComplaintResponse createComplaintResponse = fromJson(mvcResult, CreateComplaintResponse.class);

        Optional<Complaint> complaint = complaintRepository.findById(createComplaintResponse.id());

        assertThat(complaint.isPresent()).isTrue();
        assertThat(complaint.get().getCustomer().getEmail()).isEqualTo(createComplaintRequest.customerEmail());
        assertThat(complaint.get().getProduct().getId()).isEqualTo(createComplaintRequest.productId());
        assertThat(complaint.get().getDescription()).isEqualTo(createComplaintRequest.description());
    }

    @Test
    void shouldReturnExistComplaint() throws Exception {
        //given
        Product product = TestData.createProduct(TestData.PRODUCT_ID, TestData.PRODUCT_NAME, TestData.PRODUCT_PRICE);

        Complaint complaintToSave = TestData.createComplaint(TestData.COMPLAINT_ID,
                product,
                Customer.builder().email(TestData.NEW_CUSTOMER_EMAIL).build(),
                TestData.COMPLAINT_COUNT,
                TestData.COMPLAINT_DESCRIPTION);
        complaintRepository.saveAndFlush(complaintToSave);

        CreateComplaintRequest createComplaintRequest =
                new CreateComplaintRequest(TestData.PRODUCT_ID, TestData.NEW_CUSTOMER_EMAIL, TestData.NEW_COMPLAINT_DESC);

        //when
        MvcResult mvcResult = mockMvc.perform(post(CREATE_COMPLAINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createComplaintRequest)))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn();

        //then
        CreateComplaintResponse createComplaintResponse = fromJson(mvcResult, CreateComplaintResponse.class);

        Optional<Complaint> complaint = complaintRepository.findById(createComplaintResponse.id());

        assertThat(complaint.isPresent()).isTrue();
        assertThat(complaint.get().getId()).isEqualTo(complaintToSave.getId());
        assertThat(complaint.get().getProduct().getId()).isEqualTo(complaintToSave.getProduct().getId());
        assertThat(complaint.get().getDescription()).isEqualTo(complaintToSave.getDescription());
    }

    @Test
    void shouldGetComplaintById() throws Exception {
        //given
        Complaint complaintToSave = TestData.createComplaint(TestData.COMPLAINT_ID,
                Product.builder().build(),
                Customer.builder().build(),
                TestData.COMPLAINT_COUNT,
                TestData.COMPLAINT_DESCRIPTION);
        complaintRepository.saveAndFlush(complaintToSave);

        //when
        MvcResult mvcResult = mockMvc.perform(get(GET_COMPLAINT_URL, TestData.COMPLAINT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        ComplaintResponse complaintResponse = fromJson(mvcResult, ComplaintResponse.class);

        Optional<Complaint> complaint = complaintRepository.findById(TestData.COMPLAINT_ID);
        assertThat(complaint.isPresent()).isTrue();
        assertThat(complaint.get().getCustomer().getEmail()).isEqualTo(complaintResponse.customerEmail());
        assertThat(complaint.get().getProduct().getId()).isEqualTo(complaintResponse.productId());
        assertThat(complaint.get().getDescription()).isEqualTo(complaintResponse.description());
    }

    @Test
    void shouldGetPagedComplaints() throws Exception {
        //given
        Complaint complaintToSave = TestData.createComplaint(TestData.COMPLAINT_ID,
                Product.builder().build(),
                Customer.builder().build(),
                TestData.COMPLAINT_COUNT,
                TestData.COMPLAINT_DESCRIPTION);
        complaintRepository.saveAndFlush(complaintToSave);

        String page = "0";
        String size = "10";

        //when
        MvcResult mvcResult = mockMvc.perform(get(GET_PAGED_COMPLAINT_URL)
                        .param("page", page)
                        .param("size", size)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        ComplaintListResponse complaintListResponse = fromJson(mvcResult, ComplaintListResponse.class);

        List<Complaint> complaints = complaintRepository.findAll();

        assertThat(complaintListResponse).isNotNull();
        assertThat(complaintListResponse.page()).isEqualTo(Integer.valueOf(page));
        assertThat(complaintListResponse.size()).isEqualTo(complaints.size());
        assertThat(complaintListResponse.complaints().size()).isEqualTo(complaints.size());
    }

    @Test
    void shouldPatchComplaint() throws Exception {
        //given
        Complaint complaintToSave = TestData.createComplaint(TestData.COMPLAINT_ID,
                Product.builder().build(),
                Customer.builder().build(),
                TestData.COMPLAINT_COUNT,
                TestData.COMPLAINT_DESCRIPTION);
        complaintRepository.saveAndFlush(complaintToSave);

        PatchComplaintRequest patchComplaintRequest =
                new PatchComplaintRequest(TestData.COMPLAINT_ID, TestData.PATCHED_COMPLAINT_DESCRIPTION);

        //when
        MvcResult mvcResult = mockMvc.perform(patch(PATCH_COMPLAINT_URL)
                        .content(objectMapper.writeValueAsString(patchComplaintRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Optional<Complaint> complaint = complaintRepository.findById(complaintToSave.getId());

        String result = fromJson(mvcResult, String.class);
        assertThat(complaint.isPresent()).isTrue();
        assertThat(complaint.get().getDescription()).isEqualTo(TestData.PATCHED_COMPLAINT_DESCRIPTION);
        assertThat(result).isEqualTo(UPDATED_COMPLAINT_RESPONSE);
    }
}
