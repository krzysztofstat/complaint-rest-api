package pl.empik.complaint_rest_api.config;

import com.maxmind.geoip2.DatabaseReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

@Configuration
public class AppConfig {

    @Value("${geo-lite.database.path}")
    private String geoLiteDatabasePath;

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(10));
        requestFactory.setReadTimeout(Duration.ofSeconds(30));

        return builder
                .requestFactory(requestFactory)
                .build();
    }

    @Bean
    public DatabaseReader databaseReader() throws IOException {
        ClassPathResource resource = new ClassPathResource(geoLiteDatabasePath);
        try (InputStream inputStream = resource.getInputStream()) {
            return new DatabaseReader.Builder(inputStream).build();
        }
    }
}