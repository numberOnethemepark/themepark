package com.business.themeparkservice.hashtag.domain.repository;

import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;

import java.util.Optional;
import java.util.UUID;

public interface HashtagRepository {
    HashtagEntity save(HashtagEntity hashtagEntity);

    void flush();

    Optional<HashtagEntity> findById(UUID tagId);
}
