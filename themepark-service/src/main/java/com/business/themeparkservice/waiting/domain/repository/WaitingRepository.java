package com.business.themeparkservice.waiting.domain.repository;

import com.business.themeparkservice.waiting.domain.entity.WaitingEntity;
import com.business.themeparkservice.waiting.domain.vo.WaitingStatus;
import feign.Param;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WaitingRepository {

    @Query(value = "select * from themepark_service.p_waitings w where w.themepark_id = :themeparkId and w.waiting_status = 'WAITING' order by w.waiting_number desc limit 1 for share",nativeQuery = true)
    Optional<WaitingEntity> findLastWaitingNumber(UUID themeparkId,WaitingStatus waitingStatus);

    int countByThemeparkIdAndWaitingStatus(@NotNull(message = "테마파크번호를 입력해주세요") UUID themeparkId,WaitingStatus waitingStatus);

    int countByThemeparkIdAndUserIdAndWaitingStatus(UUID themeparkId, Long userId,WaitingStatus waitingStatus);

    @Query("select count(*) from WaitingEntity w " +
            "where w.waitingNumber < :waitingNumber AND w.themeparkId = :themeparkId AND w.waitingStatus = 'WAITING'")
    int checkingMyWaitingLeft(Integer waitingNumber,UUID themeparkId);

    Optional<WaitingEntity> findByIdAndWaitingStatus(UUID id,WaitingStatus waitingStatus);
}
