package it.developer.temperatureservice.repository;

import it.developer.temperatureservice.model.SensorData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SensorDataRepository extends MongoRepository<SensorData, String> {

    List<SensorData> findBySensorIdAndTimestampBetween(String sensorId, Long from, Long to);

    List<SensorData> findBySensorId(String sensorId);
}
