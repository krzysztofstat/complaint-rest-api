package pl.empik.complaint_rest_api.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.empik.complaint_rest_api.dto.request.CreateComplaintRequest;
import pl.empik.complaint_rest_api.model.Customer;
import pl.empik.complaint_rest_api.model.Complaint;
import pl.empik.complaint_rest_api.model.Product;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ComplaintBuilder {

    public static Complaint buildComplaint(CreateComplaintRequest createComplaintRequest, String country, Customer customer, Product product) {
        return Complaint.builder()
                .customer(customer)
                .product(product)
                .description(createComplaintRequest.description())
                .complaintCountry(country)
                .build();
    }
}
