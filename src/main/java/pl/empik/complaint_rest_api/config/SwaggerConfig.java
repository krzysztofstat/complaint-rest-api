package pl.empik.complaint_rest_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(buildInfo());
    }

    private Info buildInfo() {
        return new Info()
                .title("Complaint REST API")
                .version("v0.0.1");
    }
}
