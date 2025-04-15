package com.business.themeparkservice.waiting.domain.repository;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface WaitingRepository {
    int countByThemeparkId(@NotNull(message = "테마파크번호를 입력해주세요") UUID themeparkId);
}
