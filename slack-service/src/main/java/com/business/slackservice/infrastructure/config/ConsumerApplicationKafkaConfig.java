package com.business.slackservice.infrastructure.config;

import com.business.slackservice.application.dto.v1.request.slack.ReqSlackPostDTOApiV1;
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

// 이 클래스는 Kafka 컨슈머 설정을 위한 Spring 설정 클래스입니다.
@EnableKafka // Kafka 리스너를 활성화하는 어노테이션입니다.
@Configuration // Spring 설정 클래스로 선언하는 어노테이션입니다.
public class ConsumerApplicationKafkaConfig {

    @Bean
    public ConsumerFactory<String, ReqSlackPostDTOApiV1> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "51.20.67.219:8088");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        // JSON 역직렬화 설정
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.business.slackservice.application.dto.v3.request.slack");
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.business.slackservice.application.dto.v3.request.slack.ReqSlackPostDTOApiV3");
        configProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(ReqSlackPostDTOApiV1.class))
        );
    }

    // Kafka 리스너 컨테이너 팩토리를 생성하는 빈을 정의합니다.
    // ConcurrentKafkaListenerContainerFactory는 Kafka 메시지를 비동기적으로 수신하는 리스너 컨테이너를 생성하는 데 사용됩니다.
    // 이 팩토리는 @KafkaListener 어노테이션이 붙은 메서드들을 실행할 컨테이너를 제공합니다.
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReqSlackPostDTOApiV1> kafkaListenerContainerFactory() {
        // ConcurrentKafkaListenerContainerFactory를 생성합니다.
        ConcurrentKafkaListenerContainerFactory<String, ReqSlackPostDTOApiV1> factory = new ConcurrentKafkaListenerContainerFactory<>();
        // 컨슈머 팩토리를 리스너 컨테이너 팩토리에 설정합니다.
        factory.setConsumerFactory(consumerFactory());
        // 설정된 리스너 컨테이너 팩토리를 반환합니다.
        return factory;
    }
}