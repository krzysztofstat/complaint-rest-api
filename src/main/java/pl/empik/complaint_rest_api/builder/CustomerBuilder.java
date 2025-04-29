package pl.empik.complaint_rest_api.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.empik.complaint_rest_api.model.Customer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerBuilder {

    public static Customer buildCustomer(String customerEmail) {
        return Customer.builder()
                .email(customerEmail)
                .build();
    }
}
