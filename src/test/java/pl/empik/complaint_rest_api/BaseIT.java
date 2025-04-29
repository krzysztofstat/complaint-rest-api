package pl.empik.complaint_rest_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class BaseIT {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private static final MySQLContainer<?> serverContainer =
            new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"));

    static {
        serverContainer.start();
    }

    @DynamicPropertySource
    public static void databaseContainerConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", serverContainer::getJdbcUrl);
        registry.add("spring.datasource.password", serverContainer::getPassword);
        registry.add("spring.datasource.username", serverContainer::getUsername);
        registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    protected <T> T fromJson(MvcResult mvcResult, Class<T> clazz) {
        try {
            String json = mvcResult.getResponse().getContentAsString();
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode responseNode = jsonNode.get("response");
            return objectMapper.treeToValue(responseNode, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
