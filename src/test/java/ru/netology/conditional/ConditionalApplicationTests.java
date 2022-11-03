package ru.netology.conditional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConditionalApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final GenericContainer<?> devapp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    private static final GenericContainer<?> prodapp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devapp.start();
        prodapp.start();
    }

    @Test
    void contextLoads() {

        ResponseEntity<String> entityFromDevapp = restTemplate.
                getForEntity("http://localhost:" + devapp.getMappedPort(8080) + "/profile", String.class);
        System.out.println("devapp " + entityFromDevapp.getBody());
        Assertions.assertEquals("Current profile is dev", entityFromDevapp.getBody());

        ResponseEntity<String> entityFromProdapp = restTemplate.
                getForEntity("http://localhost:" + prodapp.getMappedPort(8081) + "/profile", String.class);
        System.out.println("prodapp " + entityFromProdapp.getBody());
        Assertions.assertEquals("Current profile is production", entityFromProdapp.getBody());
    }

}
