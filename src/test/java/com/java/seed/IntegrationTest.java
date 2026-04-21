package com.java.seed;

import com.java.seed.user_order.UserOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    static final Network network = Network.newNetwork();

    static PostgreSQLContainer<?> postgres;

    static KafkaContainer kafka;

    static GenericContainer<?> schemaRegistry;

    @Autowired
    private UserOrderRepository userOrderRepository;

    static {
        postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17"))
                .withNetwork(network)
                .withNetworkAliases("postgres")
                .withReuse(true);

        kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.8.0"))
                .withNetwork(network)
                .withNetworkAliases("kafka")
                .withReuse(true);

        schemaRegistry = new GenericContainer<>(DockerImageName.parse("confluentinc/cp-schema-registry:7.8.0"))
                .withNetwork(network)
                .withExposedPorts(8081)
                .withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS", "PLAINTEXT://kafka:9092")
                .withEnv("SCHEMA_REGISTRY_HOST_NAME", "schema-registry")
                .withEnv("SCHEMA_REGISTRY_LISTENERS", "http://0.0.0.0:8081")
                .dependsOn(kafka)
                .withReuse(true);

        Startables.deepStart(postgres, kafka, schemaRegistry).join();
        System.setProperty("SCHEMA_REGISTRY_URL", "http://" + schemaRegistry.getHost() + ":" + schemaRegistry.getMappedPort(8081));
    }

    @BeforeEach
    public void setup() {
        userOrderRepository.deleteAll();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://%s:%s/postgres".formatted(
                postgres.getHost(), postgres.getMappedPort(5432)));
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.kafka.producer.properties.schema.registry.url", () ->
                "http://%s:%s".formatted(schemaRegistry.getHost(), schemaRegistry.getMappedPort(8081)));
    }
}
