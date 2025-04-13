package com.business.themeparkservice.hashtag.domain.entity;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkHashtagEntity;
import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "p_hashtags")
@NoArgsConstructor
public class HashtagEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hashtag_id",nullable = false)
    private UUID id;

    @Column(name = "hashtag_name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL)
    private List<ThemeparkHashtagEntity> themeparkHashtagEntityList;

    @Builder
    public HashtagEntity(String name) {
        this.name = name;
    }
}
