package pl.empik.complaint_rest_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.empik.complaint_rest_api.builder.CustomerBuilder;
import pl.empik.complaint_rest_api.model.Customer;
import pl.empik.complaint_rest_api.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer fetchOrCreateCustomer(String customerEmail) {
        return customerRepository.findByEmail(customerEmail)
                .orElseGet(() -> createCustomer(customerEmail));
    }

    private Customer createCustomer(String customerEmail) {
        return CustomerBuilder.buildCustomer(customerEmail);
    }
}
