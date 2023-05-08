package com.store.car.message;

import com.store.car.dto.CarPostDTO;
import com.store.car.service.CarPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerMessage {

    private final Logger LOG = LoggerFactory.getLogger(KafkaConsumerMessage.class);

    @Autowired
    private CarPostService carPostService;

    // o KafkaListener é responsável por ficar ouvindo e monitorando o tópico abaixo atodo instante
    // quando uma mensagem cair dentro do referido topico e dentro do group id especificado, ele a captura
    @KafkaListener(topics = "car-post-topic", groupId = "store-posts-group")
    public void listening(CarPostDTO carPost) {
        LOG.info("CAR STORE - Received Car Post information: {}", carPost);

        // envia o objeto para ser salvo no banco
        carPostService.newPostDetails(carPost);
    }

}
