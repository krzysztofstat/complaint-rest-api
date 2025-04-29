package pl.empik.complaint_rest_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.empik.complaint_rest_api.builder.ComplaintBuilder;
import pl.empik.complaint_rest_api.dto.request.CreateComplaintRequest;
import pl.empik.complaint_rest_api.dto.request.PatchComplaintRequest;
import pl.empik.complaint_rest_api.dto.response.ComplaintListResponse;
import pl.empik.complaint_rest_api.dto.response.ComplaintResponse;
import pl.empik.complaint_rest_api.dto.response.CreateComplaintResponse;
import pl.empik.complaint_rest_api.exception.ComplaintNotFoundException;
import pl.empik.complaint_rest_api.model.Customer;
import pl.empik.complaint_rest_api.model.Complaint;
import pl.empik.complaint_rest_api.model.Product;
import pl.empik.complaint_rest_api.repository.ComplaintRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final CountryService countryService;
    private final ProductService productService;
    private final CustomerService customerService;

    public ComplaintResponse findById(String id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ComplaintNotFoundException(id));

        return ComplaintResponse.fromComplaint(complaint);
    }

    public ComplaintListResponse findComplaints(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Complaint> complaintPage = complaintRepository.findAll(pageable);
        List<ComplaintResponse> complaintsView = complaintPage.getContent().stream()
                .map(ComplaintResponse::fromComplaint)
                .toList();

        return ComplaintListResponse.builder()
                .complaints(complaintsView)
                .page(page)
                .size(complaintsView.size())
                .build();
    }

    public CreateComplaintResponse createOrFetchComplaint(CreateComplaintRequest createComplaintRequest, String addressIp) {
        return complaintRepository.findByCustomerEmailAndProductId(createComplaintRequest.customerEmail(), createComplaintRequest.productId())
                .map(this::incrementAndSaveComplaint)
                .orElseGet(() -> createComplaint(createComplaintRequest, addressIp));
    }

    private CreateComplaintResponse incrementAndSaveComplaint(Complaint complaint) {
        complaint.setComplaintCount(complaint.getComplaintCount() + 1);
        complaintRepository.save(complaint);

        return CreateComplaintResponse.fromComplaint(complaint);
    }

    private CreateComplaintResponse createComplaint(CreateComplaintRequest newComplaintDto, String ip) {
        String country = countryService.obtainCountryByIp(ip);
        Product product = productService.findProductById(newComplaintDto.productId());
        Customer customer = customerService.fetchOrCreateCustomer(newComplaintDto.customerEmail());

        Complaint complaint = ComplaintBuilder.buildComplaint(newComplaintDto, country, customer, product);
        complaintRepository.save(complaint);

        return CreateComplaintResponse.fromComplaint(complaint);
    }

    public void patchComplaint(PatchComplaintRequest patchComplaintRequest) {
        Complaint complaint = complaintRepository.findById(patchComplaintRequest.complaintId())
                .orElseThrow(() -> new ComplaintNotFoundException(patchComplaintRequest.complaintId()));

        complaint.setDescription(patchComplaintRequest.description());
        complaint.getLifecycle().setModifyDate(LocalDateTime.now());

        complaintRepository.save(complaint);
    }
}