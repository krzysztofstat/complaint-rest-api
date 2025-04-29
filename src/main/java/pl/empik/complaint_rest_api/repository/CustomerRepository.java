package pl.empik.complaint_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.empik.complaint_rest_api.model.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);
}
