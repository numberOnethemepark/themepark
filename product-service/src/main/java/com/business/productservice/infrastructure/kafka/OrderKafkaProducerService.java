package com.business.productservice.infrastructure.kafka;

import com.business.productservice.infrastructure.kafka.dto.ReqStockDecreaseFailDTOApiV3;
import com.business.productservice.infrastructure.kafka.dto.ReqStockDecreaseSuccessDTOApiV3;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderKafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendStockDecreaseFailMessage(ReqStockDecreaseFailDTOApiV3 dto) {
        kafkaTemplate.send("stock-decrease-fail-topic", dto.getOrderId(), dto);
    }

    public void sendStockDecreaseSuccessMessage(ReqStockDecreaseSuccessDTOApiV3 dto) {
        kafkaTemplate.send("stock-decrease-success-topic", dto.getOrderId(), dto);
    }
}
