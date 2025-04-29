package pl.empik.complaint_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.empik.complaint_rest_api.model.Product;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByProductNameAndPrice(String productId, BigDecimal price);
}
