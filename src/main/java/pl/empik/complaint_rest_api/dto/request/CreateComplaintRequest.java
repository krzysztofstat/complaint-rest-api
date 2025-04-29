package pl.empik.complaint_rest_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateComplaintRequest(@NotBlank String productId,
                                     @NotBlank @Email String customerEmail,
                                     @NotBlank String description) {
}
