package com.business.themeparkservice.hashtag.domain.entity;

import com.business.themeparkservice.themepark.domain.entity.ThemeparkHashtagEntity;
import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "p_hashtags")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor
public class HashtagEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hashtag_id",nullable = false)
    private UUID id;

    @Column(name = "hashtag_name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ThemeparkHashtagEntity> themeparkHashtagEntityList;

    @Builder
    public HashtagEntity(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }

}
