package it.developer.temperatureservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DataRequest {

    private String sensorId;

    private BigDecimal temperature;

    private BigDecimal humidity;

    private Long timestamp;
}
