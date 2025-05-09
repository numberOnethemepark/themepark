package com.sparta.orderservice.order.application.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.themepark.common.application.dto.ResDTO;
import com.sparta.orderservice.order.application.dto.reponse.ResProductGetByIdDTOApi;
import com.sparta.orderservice.order.domain.entity.OrderEntity;
import com.sparta.orderservice.order.domain.repository.OrderRepository;
import com.sparta.orderservice.order.infrastructure.feign.ProductFeignClientApi;
import com.sparta.orderservice.order.infrastructure.kafka.dto.req.ReqProductKafkaDto;
import com.sparta.orderservice.order.infrastructure.kafka.service.KafkaService;
import com.sparta.orderservice.order.presentation.dto.v3.request.ReqOrdersPostDtoApiV3;
import io.micrometer.tracing.Tracer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
public class OrderTransactionHelper {

    private final OrderRepository orderRepository;
    private final ProductFeignClientApi productFeignClientApi;
    private final RedisTemplate<String, ResProductGetByIdDTOApi> productRedisTemplate;
    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;

    public OrderTransactionHelper(
            OrderRepository orderRepository,
            ProductFeignClientApi productFeignClientApi,
            @Qualifier("ProductRedisTemplate") RedisTemplate<String, ResProductGetByIdDTOApi> productRedisTemplate,
            KafkaService kafkaService,
            ObjectMapper objectMapper,
            Tracer tracer
    ) {
        this.orderRepository = orderRepository;
        this.productFeignClientApi = productFeignClientApi;
        this.productRedisTemplate = productRedisTemplate;
        this.kafkaService = kafkaService;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public ResProductGetByIdDTOApi getRedis(String redisProductKey){
        Object raw = productRedisTemplate.opsForValue().get(redisProductKey);
        if (raw != null) {
            return objectMapper.convertValue(raw, ResProductGetByIdDTOApi.class);
        }else{
            return null;
        }
    }

    @Transactional
    public boolean checkProduct(ResProductGetByIdDTOApi product, ReqOrdersPostDtoApiV3 reqOrdersPostDtoApiV3, String redisProductKey){
        if (product == null) {
            // Redis 캐시 미스 → Product 서비스 호출 후 캐싱
            ResDTO<ResProductGetByIdDTOApi> resProduct = productFeignClientApi.getBy(reqOrdersPostDtoApiV3.getOrder().getProductId());
            product = resProduct.getData();
            productRedisTemplate.opsForValue().set(redisProductKey, product, Duration.ofMinutes(10)); // TTL 10분 예시
        }
        return "EVENT".equals(product.getProduct().getProductType());
    }

    @Transactional
    public void decreaseStock(String orderId, String productId, boolean isEventProduct){
        if (isEventProduct) {
            ReqProductKafkaDto reqProductKafkaDto = ReqProductKafkaDto.builder()
                    .productId(productId)
                    .orderId(orderId)
                    .build();
            kafkaService.sendOrderAsync("stock-decrease-topic", reqProductKafkaDto);
        }
    }

    @Transactional
    public OrderEntity createOrder(Long userId, ReqOrdersPostDtoApiV3 reqOrdersPostDtoApiV3){
        OrderEntity orderEntity = OrderEntity.createOrder(
                reqOrdersPostDtoApiV3.getOrder().getProductId(),
                reqOrdersPostDtoApiV3.getOrder().getAmount(),
                reqOrdersPostDtoApiV3.getOrder().getSlackId(),
                userId
        );
        orderRepository.save(orderEntity);
        return orderEntity;
    }

    @Transactional
    public void restoreStock(String productId){
        kafkaService.sendRestoreAsync("stock-restore-topic", productId);
    }

    public void cancelOrder(OrderEntity orderEntity) {
        orderRepository.delete(orderEntity);
    }
}
