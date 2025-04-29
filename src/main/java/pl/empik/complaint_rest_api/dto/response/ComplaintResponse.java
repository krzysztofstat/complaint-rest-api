package pl.empik.complaint_rest_api.dto.response;

import lombok.Builder;
import pl.empik.complaint_rest_api.model.Complaint;

import java.time.LocalDateTime;

@Builder
public record ComplaintResponse(String id, String description, LocalDateTime creationDate, String complaintCountry,
                                String productId, String productName, String customerId, String customerEmail,
                                long complaintCount) {

    public static ComplaintResponse fromComplaint(Complaint complaint) {
        return ComplaintResponse.builder()
                .id(complaint.getId())
                .description(complaint.getDescription())
                .complaintCountry(complaint.getComplaintCountry())
                .productId(complaint.getProduct().getId())
                .productName(complaint.getProduct().getProductName())
                .customerId(complaint.getCustomer().getId())
                .customerEmail(complaint.getCustomer().getEmail())
                .complaintCount(complaint.getComplaintCount())
                .build();
    }
}