package pl.empik.complaint_rest_api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import pl.empik.complaint_rest_api.model.Complaint;

import java.time.LocalDateTime;

@Builder
public record CreateComplaintResponse(String id,
                                      @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
                                      LocalDateTime createdAt) {

    public static CreateComplaintResponse fromComplaint(Complaint complaint) {
        return CreateComplaintResponse.builder()
                .id(complaint.getId())
                .createdAt(complaint.getLifecycle().getCreateDate())
                .build();
    }
}
