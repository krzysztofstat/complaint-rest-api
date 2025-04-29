package pl.empik.complaint_rest_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PatchComplaintRequest(@NotBlank String complaintId, @NotBlank String description) {
}