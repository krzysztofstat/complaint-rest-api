package pl.empik.complaint_rest_api.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String email;
    @Embedded
    @Builder.Default
    private Lifecycle lifecycle = Lifecycle.builder().build();
    @Version
    private Integer version;
}
