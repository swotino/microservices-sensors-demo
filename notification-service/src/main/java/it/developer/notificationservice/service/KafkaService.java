package it.developer.notificationservice.service;

import it.developer.notificationservice.event.DataEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaService {

    @KafkaListener(topics = "data-event", groupId = "notification-service")
    public void handleMessage(DataEvent dataEvent) {
        log.info("Received message: {}", dataEvent);
    }
}
