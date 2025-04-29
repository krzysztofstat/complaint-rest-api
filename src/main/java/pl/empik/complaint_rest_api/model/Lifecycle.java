package pl.empik.complaint_rest_api.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Lifecycle {

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime modifyDate;
}
