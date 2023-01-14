package it.developer.temperatureservice.service;

import it.developer.temperatureservice.dto.DataRequest;
import it.developer.temperatureservice.dto.DataResponse;
import it.developer.temperatureservice.event.DataEvent;
import it.developer.temperatureservice.model.SensorData;
import it.developer.temperatureservice.repository.SensorDataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TemperatureService {

    private final SensorDataRepository sensorDataRepository;

    private final KafkaTemplate<String, DataEvent> kafkaTemplate;

    public void postData(DataRequest dataRequest) {

        log.info("Received data: {}", dataRequest);
        var sensorData = SensorData.builder()
                .sensorId(dataRequest.getSensorId())
                .temperature(dataRequest.getTemperature())
                .humidity(dataRequest.getHumidity())
                .timestamp(dataRequest.getTimestamp())
                .build();

        var data = sensorDataRepository.save(sensorData);
        log.info("Saved data: {}", data);

        // Send by Kafka
        kafkaTemplate.send("data-event", mapToEvent(data));
        log.info("Sent data by Kafka");
    }

    public List<DataResponse> getData(String sensorId, Long from, Long to) {

        var list = sensorDataRepository.findBySensorIdAndTimestampBetween(sensorId, from, to);
        return list.stream().map(this::mapToResponse).toList();
    }

    public List<DataResponse> getAllData() {

        var list = sensorDataRepository.findAll();
        return list.stream().map(this::mapToResponse).toList();
    }


    private DataResponse mapToResponse(SensorData sensorData) {

        return DataResponse.builder()
                .sensorId(sensorData.getSensorId())
                .temperature(sensorData.getTemperature())
                .humidity(sensorData.getHumidity())
                .timestamp(sensorData.getTimestamp())
                .build();
    }

    private DataEvent mapToEvent(SensorData sensorData) {

        return DataEvent.builder()
                .id(sensorData.getId())
                .sensorId(sensorData.getSensorId())
                .temperature(sensorData.getTemperature())
                .humidity(sensorData.getHumidity())
                .timestamp(sensorData.getTimestamp())
                .build();
    }
}
