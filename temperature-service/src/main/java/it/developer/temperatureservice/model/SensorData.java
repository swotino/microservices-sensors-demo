package it.developer.temperatureservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "sensorData")                    // MongoDB Annotation
@Data @NoArgsConstructor @AllArgsConstructor @Builder   // Lombok annotations
public class SensorData {

    @Id
    private String id;

    private String sensorId;

    private BigDecimal temperature;

    private BigDecimal humidity;

    private Long timestamp;
}
