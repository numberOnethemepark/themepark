package com.sparta.orderservice.order.infrastructure.kafka.config;

import com.sparta.orderservice.order.infrastructure.kafka.dto.res.ResProductKafkaDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumeConfig {
    @Bean
    public ConsumerFactory<String, ResProductKafkaDto> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:8088");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "order-service");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        configProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        JsonDeserializer<ResProductKafkaDto> deserializer = new JsonDeserializer<>(ResProductKafkaDto.class);
        deserializer.setRemoveTypeHeaders(true);
        deserializer.setUseTypeHeaders(false);
        deserializer.addTrustedPackages("*");


        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(ResProductKafkaDto.class))
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ResProductKafkaDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ResProductKafkaDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
