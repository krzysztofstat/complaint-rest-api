package pl.empik.complaint_rest_api.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CountryResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.empik.complaint_rest_api.client.ip_api.IpApiClient;

import java.net.InetAddress;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryService {
    
    private final IpApiClient ipApiClient;
    private final DatabaseReader databaseReader;

    private static final String UNKNOWN_COUNTRY = "UNKNOWN";

    @CircuitBreaker(name = "obtainCountryByIp", fallbackMethod = "obtainCountryInGeoDbFallback")
    public String obtainCountryByIp(String ip) {
        return Optional.ofNullable(ipApiClient.findCountryByIp(ip).country())
                .orElseGet(() -> obtainCountryInGeoDb(ip));
    }

    private String obtainCountryInGeoDbFallback(String ipAddress, Throwable t) {
        log.warn("Fallback triggered for obtainCountryByIp due to: {}", t.getMessage());
        return obtainCountryByIp(ipAddress);
    }

    private String obtainCountryInGeoDb(String ipAddress) {
        try {
            InetAddress ipAddressObj = InetAddress.getByName(ipAddress);
            CountryResponse response = databaseReader.country(ipAddressObj);
            return response.getCountry().getName();
        } catch (Exception e) {
            log.error("Country could not be found in database");
            return UNKNOWN_COUNTRY;
        }
    }
}
