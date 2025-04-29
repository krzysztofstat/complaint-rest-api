package pl.empik.complaint_rest_api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.empik.complaint_rest_api.dto.request.CreateComplaintRequest;
import pl.empik.complaint_rest_api.dto.request.PatchComplaintRequest;
import pl.empik.complaint_rest_api.dto.response.ApiResponse;
import pl.empik.complaint_rest_api.dto.response.CreateComplaintResponse;
import pl.empik.complaint_rest_api.service.ComplaintService;
import pl.empik.complaint_rest_api.dto.response.ComplaintResponse;
import pl.empik.complaint_rest_api.dto.response.ComplaintListResponse;
import pl.empik.complaint_rest_api.utils.IpExtractor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/complaint")
public class ComplaintController {

    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_PAGE_SIZE = "10";
    private static final String UPDATED_COMPLAINT_RESPONSE = "Complaint updated";

    private final ComplaintService complaintService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ComplaintResponse>> findComplaint(@PathVariable String id) {
        ComplaintResponse complaintResponse = complaintService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(complaintResponse));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<ComplaintListResponse>> findComplaints(@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                                             @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size) {
        ComplaintListResponse complaintListResponse = complaintService.findComplaints(page, size);
        return ResponseEntity.ok(ApiResponse.success(complaintListResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateComplaintResponse>> createComplaint(@RequestBody @Valid CreateComplaintRequest createComplaintRequest,
                                                                                HttpServletRequest request) {
        CreateComplaintResponse createdComplaint = complaintService.createOrFetchComplaint(createComplaintRequest, IpExtractor.extractClientIp(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createdComplaint));
    }

    @PatchMapping(value = "/patch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> patchComplaint(@RequestBody @Valid PatchComplaintRequest patchComplaintRequest) {
        complaintService.patchComplaint(patchComplaintRequest);
        return ResponseEntity.ok(ApiResponse.success(UPDATED_COMPLAINT_RESPONSE));
    }
}