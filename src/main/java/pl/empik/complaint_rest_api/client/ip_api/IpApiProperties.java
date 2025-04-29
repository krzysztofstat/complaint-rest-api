package pl.empik.complaint_rest_api.client.ip_api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "ip-api")
@Component
@Data
public class IpApiProperties {

    private String url;
}
