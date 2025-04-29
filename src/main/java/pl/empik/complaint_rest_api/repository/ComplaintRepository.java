package pl.empik.complaint_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.empik.complaint_rest_api.model.Complaint;

import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, String> {
    @Query("SELECT c FROM Complaint c " +
            "WHERE c.customer.email = :customerEmail " +
            "AND c.product.id = :productId")
    Optional<Complaint> findByCustomerEmailAndProductId(String customerEmail, String productId);
}