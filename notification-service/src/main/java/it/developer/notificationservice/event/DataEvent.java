package it.developer.notificationservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DataEvent {

    private String id;

    private String sensorId;

    private BigDecimal temperature;

    private BigDecimal humidity;

    private long timestamp;
}
