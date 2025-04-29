package pl.empik.complaint_rest_api;

import pl.empik.complaint_rest_api.model.Complaint;
import pl.empik.complaint_rest_api.model.Customer;
import pl.empik.complaint_rest_api.model.Product;

import java.math.BigDecimal;

public class TestData {
    public static final String NEW_CUSTOMER_EMAIL = "newemail@email.com";
    public static final String NEW_COMPLAINT_DESC = "Wrong price";
    public static final String PRODUCT_ID = "productId";
    public static final String PRODUCT_NAME = "productName";
    public static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(100, 2);
    public static final String COMPLAINT_ID = "complaintId";
    public static final int COMPLAINT_COUNT = 2;
    public static final String COMPLAINT_DESCRIPTION = "desc";
    public static final String PATCHED_COMPLAINT_DESCRIPTION = "patched";

    public static Customer createCustomer(String email) {
        return Customer.builder()
                .email(email)
                .build();
    }

    public static Product createProduct(String id, String productName, BigDecimal price) {
        return Product.builder()
                .id(id)
                .productName(productName)
                .price(price)
                .build();
    }

    public static Complaint createComplaint(String id, Product product, Customer customer, int complaintCount, String description) {
        return Complaint.builder()
                .id(id)
                .product(product)
                .customer(customer)
                .complaintCount(complaintCount)
                .description(description)
                .build();
    }

}