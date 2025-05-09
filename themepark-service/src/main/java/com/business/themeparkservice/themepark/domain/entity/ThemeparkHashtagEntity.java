package com.business.themeparkservice.themepark.domain.entity;

import com.business.themeparkservice.hashtag.domain.entity.HashtagEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "p_themepark_hashtags")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ThemeparkHashtagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "themepark_hashtag_id", nullable = false)
    private UUID themeparkHashtagId;


    @ManyToOne
    @JoinColumn(name = "themepark_id")
    ThemeparkEntity themepark;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    HashtagEntity hashtag;

}
