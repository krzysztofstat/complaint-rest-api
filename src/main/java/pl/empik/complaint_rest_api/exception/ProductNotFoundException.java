package pl.empik.complaint_rest_api.exception;

public class ProductNotFoundException extends RuntimeException {
    private static final String ERROR_COMPLAINT_NOT_FOUND = "Product with id %s has not been found";

    public ProductNotFoundException(String productId) {
        super(String.format(ERROR_COMPLAINT_NOT_FOUND, productId));
    }
}