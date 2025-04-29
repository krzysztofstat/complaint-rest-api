package pl.empik.complaint_rest_api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ComplaintListResponse(List<ComplaintResponse> complaints, int page, int size) {
}
