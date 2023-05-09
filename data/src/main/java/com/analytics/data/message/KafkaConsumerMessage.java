package com.analytics.data.message;

import com.analytics.data.dto.CarPostDTO;
import com.analytics.data.service.PostAnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerMessage {

    private final Logger LOG = LoggerFactory.getLogger(KafkaConsumerMessage.class);
    @Autowired
    private PostAnalyticsService postAnalyticsService;

    // o KafkaListener é responsável por ficar ouvindo e monitorando o tópico abaixo atodo instante
    // quando uma mensagem cair dentro do referido topico e dentro do group id especificado, ele a captura
    @KafkaListener(topics = "car-post-topic", groupId = "analytics-posts-group")
    public void listening(@Payload CarPostDTO carPost) {

        System.out.println("Received Car Post information : " + carPost);
        LOG.info("ANALYTICS DATA - Received Car Post information: {}", carPost);

        // envia o objeto para ser salvo no banco
        postAnalyticsService.saveDataAnalytics(carPost);

    }

}
