package pl.empik.complaint_rest_api.exception;

public class ComplaintNotFoundException extends RuntimeException {
    private static final String ERROR_COMPLAINT_NOT_FOUND = "Complaint with id %s has not been found";

    public ComplaintNotFoundException(String complaintId) {
        super(String.format(ERROR_COMPLAINT_NOT_FOUND, complaintId));
    }
}