package com.business.themeparkservice.waiting.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@RedisHash("waiting:themeparkId")
public class WaitingRedisEntity implements Serializable {

    @Id
    private String id;

    private Integer waitingNumber;
    private Integer waitingLeft;

    @Builder
    public WaitingRedisEntity(String id,Integer waitingNumber, Integer waitingLeft) {
        this.id = id;
        this.waitingNumber = waitingNumber;
        this.waitingLeft = waitingLeft;
    }
}