package com.business.themeparkservice.waiting.domain.repository;

import com.business.themeparkservice.waiting.domain.entity.WaitingEntity;
import com.business.themeparkservice.waiting.domain.vo.WaitingStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface WaitingRepository {
    int countByThemeparkId(@NotNull(message = "테마파크번호를 입력해주세요") UUID themeparkId);
    int countByThemeparkIdAndUserId(UUID themeparkId, Integer userId);

    @Query("select count(*) from WaitingEntity w " +
            "where w.waitingNumber < :waitingNumber AND w.themeparkId = :themeparkId AND w.waitingStatus = 'WAITING'")
    int checkingMyWaitingLeft(Integer waitingNumber,UUID themeparkId);

    @Query("SELECT COALESCE(MAX(w.waitingNumber), 0) FROM WaitingEntity w WHERE w.themeparkId = :themeparkId AND w.waitingStatus = 'WAITING'")
    int findLastWaitingNumber(@NotNull(message = "테마파크번호를 입력해주세요") UUID themeparkId);

    Optional<WaitingEntity> findByIdAndWaitingStatus(UUID id, WaitingStatus waitingStatus);
}
