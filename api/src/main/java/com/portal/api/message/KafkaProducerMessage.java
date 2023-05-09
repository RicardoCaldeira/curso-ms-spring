package com.portal.api.message;

import com.portal.api.dto.CarPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerMessage {

    @Autowired
    private KafkaTemplate<String, CarPostDTO> kafkaTemplate;

    // nome do topico destinado no kafka (o qual receberá as mensagens enviadas).
    // Será criado automaticamente caso não exista
    private final String KAFKA_TOPIC = "car-post-topic";

    // método final que enviará um CarPostDTO para dentro do tópico kafka especificado
    public void sendMessage(CarPostDTO carPostDTO) {
        kafkaTemplate.send(KAFKA_TOPIC, carPostDTO);
    }

}
