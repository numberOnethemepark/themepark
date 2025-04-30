package com.business.productservice.infrastructure.kafka;

import com.business.productservice.domain.product.entity.StockEntity;
import com.business.productservice.infrastructure.kafka.dto.ReqToSlackPostDTOApiV1;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SlackKafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendStockSoldOutSlack(StockEntity stockEntity) {
        String productName = stockEntity.getProduct().getName();
        ReqToSlackPostDTOApiV1 request = ReqToSlackPostDTOApiV1.createStockSoldOutMessage(productName);

        kafkaTemplate.send("slack-send", request);
    }
}
