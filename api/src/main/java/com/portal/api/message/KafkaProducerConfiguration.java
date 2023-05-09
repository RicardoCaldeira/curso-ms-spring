package com.portal.api.message;

import com.portal.api.dto.CarPostDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Configuration
public class KafkaProducerConfiguration {

    // busca informação do arquivo de configuração application.properties
    // variavel relacionada ao host bootstrapserver - host do servidor onde está executando o cluster do apache kafka
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    // fabrica produtora de mensagens para dentro do kafka, produzindo mensagens do tipo chave - valor.
    // onde a chave é uma string e o valor é um objeto de carPostDTO
    @Bean
    public ProducerFactory<String, CarPostDTO> userProducerFactory(){

        Map<String,Object> configProps = new HashMap<>();

        // copnfigurações do kafka
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer); // host do bootstrap server
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false); // indica que a mensagem não terá cabeçalhos
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // serialização via string da chave da mensagem
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName()); // serivalização via json do corpo da mensagem

        // retorna um DefaultKafkaProducerFactory com as configurações passadas
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // template da mensagem kafka para ser enviada
    // template usado em KafkaProducerMessage.java:12
    @Bean
    public KafkaTemplate<String, CarPostDTO> userKafkaTemplate(){
        return new KafkaTemplate<>(userProducerFactory());
    }

}
