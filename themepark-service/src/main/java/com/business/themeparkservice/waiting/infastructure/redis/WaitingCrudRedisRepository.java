package com.business.themeparkservice.waiting.infastructure.redis;

import com.business.themeparkservice.waiting.domain.entity.WaitingRedisEntity;
import com.business.themeparkservice.waiting.domain.repository.WaitingRedisRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WaitingCrudRedisRepository extends CrudRepository<WaitingRedisEntity, String>, WaitingRedisRepository {
}
