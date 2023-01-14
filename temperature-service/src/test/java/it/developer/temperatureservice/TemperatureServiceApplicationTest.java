package it.developer.temperatureservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.developer.temperatureservice.dto.DataRequest;
import it.developer.temperatureservice.repository.SensorDataRepository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.math.BigDecimal;

@SpringBootTest                                     // Spring Boot test annotation
@Testcontainers                                     // Testcontainers annotation
@AutoConfigureMockMvc                               // MockMvc is used to test the controller
@Slf4j                                              // Lombok annotation to generate a logger
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TemperatureServiceApplicationTest {

    // Use a MongoDB container for test
    @Container
    private static final MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:4.4.2");

    // Override the MongoDB URI with the container URI
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
    }

    @Autowired private MockMvc mockMvc;                   // MockMvc is used to test the controller

    @Autowired private SensorDataRepository sensorDataRepository; // Repository

    @Autowired private ObjectMapper objectMapper;         // Jackson ObjectMapper


    @Test @Order(1)
    void postSensorData() throws Exception {

        log.info("Test postSensorData");

        // Create a DataRequest object
        var dataRequest = DataRequest.builder()
                .sensorId("1")
                .temperature(BigDecimal.valueOf(20.0))
                .humidity(BigDecimal.valueOf(50.0))
                .timestamp(System.currentTimeMillis())
                .build();

        // Convert in Json object
        var dataString = objectMapper.writeValueAsString(dataRequest);
        log.info("dataString: {}", dataString);

        // Send the request to the controller
        var request = MockMvcRequestBuilders.post("/api/temperature")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dataString);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        var fromRepository = sensorDataRepository.findBySensorId("1");
        log.info("fromRepository: {}", fromRepository.size());
        Assertions.assertTrue(fromRepository.size() > 0);
    }

    @Test @Order(2)
    void getAllData() throws Exception {

        log.info("Test getAllData");
        // Create the request
        var request = MockMvcRequestBuilders.get("/api/temperature");

        // Send the request to the controller
        var result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();
        log.info("response: {}", response);

        Assertions.assertTrue(response.length() > 0);
    }

    @Test @Order(3)
    void getSensorData() throws Exception {

        log.info("Test getSensorData");
        // Create the request
        var from = System.currentTimeMillis() - 1800000;
        var to = System.currentTimeMillis() + 1800000;
        var request = MockMvcRequestBuilders.get("/api/temperature/1")
                .param("from", String.valueOf(from))
                .param("to", String.valueOf(to));

        // Send the request to the controller
        var result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();
        log.info("response: {}", response);

        Assertions.assertTrue(response.length() > 0);
    }
}
