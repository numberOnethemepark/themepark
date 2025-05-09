package com.business.themeparkservice.hashtag.domain.repository;

import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HashtagRepository {
    Page<HashtagEntity> findAll(Predicate predicate, Pageable pageable);
}
