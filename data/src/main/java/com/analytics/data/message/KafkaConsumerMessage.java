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

/*
A classe KafkaConsumerMessage utiliza as configurações da classe KafkaConsumerConfigs indiretamente através do método kafkaListenerContainerFactory().
No método kafkaListenerContainerFactory(), um objeto ConcurrentKafkaListenerContainerFactory é criado e configurado para consumir mensagens do Kafka.
Essa fábrica de contêineres Kafka é responsável por criar os contêineres de ouvintes que irão consumir as mensagens.

A configuração da fábrica de contêineres é feita chamando o método setConsumerFactory() e passando o resultado do método
consumerFactory() da classe KafkaConsumerConfigs. O método consumerFactory() retorna a fábrica de consumidores Kafka
configurada com as propriedades definidas na classe KafkaConsumerConfigs.

Dessa forma, as configurações definidas na classe KafkaConsumerConfigs, como servidores de inicialização,
deserializadores, etc., são utilizadas indiretamente pela classe KafkaConsumerMessage ao criar a fábrica de contêineres
Kafka no método kafkaListenerContainerFactory(). Essa fábrica será responsável por criar os contêineres de ouvintes que
irão consumir as mensagens do Kafka com as configurações especificadas.
*/
