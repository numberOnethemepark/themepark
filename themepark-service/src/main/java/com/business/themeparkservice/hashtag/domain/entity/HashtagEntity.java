package com.business.themeparkservice.hashtag.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_hashtags")
public class HashtagEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hashtag_id",nullable = false)
    private UUID id;

    @Column(name = "hashtag_name",nullable = false)
    private String hashtagName;
}
