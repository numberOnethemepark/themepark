package com.business.themeparkservice.hashtag.infastructure.persistence.hashtag;

import com.business.themeparkservice.hashtag.domain.repository.HashtagRepository;
import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HashtagJpaRepository extends JpaRepository<HashtagEntity, UUID>, HashtagRepository {
}
