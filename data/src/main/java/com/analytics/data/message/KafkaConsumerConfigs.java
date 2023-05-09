package com.analytics.data.message;

import com.analytics.data.dto.CarPostDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfigs {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // consome o que o outro ms da api produziu (o que envia mensagens para dentro do topico do
    // apache kafka. O atual ms vai trabalhar na parte de consumir informação dentro do tópico
    @Bean
    public ConsumerFactory<String, CarPostDTO> consumerFactory() {

        JsonDeserializer<CarPostDTO> deserializer = new JsonDeserializer<>(CarPostDTO.class);

        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "analytics-posts-group"); // consumo de informações do mesmo tópico do microservice car. Porem em grupos de consumidores diferentes.
        props.put(JsonDeserializer.TRUSTED_PACKAGES,"*"); // indica que confia-se nas informações que chegarão do kafka
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // desserialização da key para string
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // desserialização do corpo da msg para json

        // desserializa o json para um CarPostDTO
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(CarPostDTO.class, false));
    }

    // deixa um listener preparado para ficar ouvindo o tópico do apache kafka buscando por um CarPostDTO quando chegar
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CarPostDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CarPostDTO>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
