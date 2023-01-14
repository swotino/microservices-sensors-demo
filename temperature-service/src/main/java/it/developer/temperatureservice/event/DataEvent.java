package it.developer.temperatureservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DataEvent {

    private String id;

    private String sensorId;

    private BigDecimal temperature;

    private BigDecimal humidity;

    private Long timestamp;
}
