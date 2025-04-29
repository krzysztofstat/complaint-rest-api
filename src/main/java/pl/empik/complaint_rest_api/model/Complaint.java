package pl.empik.complaint_rest_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "complaint",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_product_customer",
                        columnNames = {"product_id", "customer_id"}
                )
        }
)
public class Complaint {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    @Column(nullable = false)
    private String description;
    @ManyToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    private String complaintCountry;
    @Builder.Default
    private long complaintCount = 1;
    @Embedded
    @Builder.Default
    private Lifecycle lifecycle = Lifecycle.builder().build();
    @Version
    private Integer version;
}