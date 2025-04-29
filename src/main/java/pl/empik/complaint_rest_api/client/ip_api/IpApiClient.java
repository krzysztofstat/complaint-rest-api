package pl.empik.complaint_rest_api.client.ip_api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import pl.empik.complaint_rest_api.client.ip_api.dto.IpApiResponse;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class IpApiClient {

    private final IpApiProperties ipApiProperties;
    private final RestClient restClient;

    public IpApiResponse findCountryByIp(String ip) {
        URI uri = UriComponentsBuilder.fromUriString(ipApiProperties.getUrl() + ip)
                .build().toUri();

        return restClient.get()
                .uri(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .toEntity(IpApiResponse.class)
                .getBody();
    }
}