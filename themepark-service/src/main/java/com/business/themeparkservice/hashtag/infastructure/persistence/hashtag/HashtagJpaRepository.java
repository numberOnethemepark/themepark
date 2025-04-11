package com.business.themeparkservice.hashtag.infastructure.persistence.hashtag;

import com.business.themeparkservice.hashtag.domain.HashtagRepository;
import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagJpaRepository extends JpaRepository<HashtagEntity, Long>, HashtagRepository {
}
