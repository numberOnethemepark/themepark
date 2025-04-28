package com.business.themeparkservice.waiting.application.dto.request.v3;

import com.business.themeparkservice.waiting.domain.entity.WaitingEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqWaitingPostDTOApiV3 {
    @Valid
    @NotNull(message = "대기정보를 입력해주세요")
    private Waiting waiting;

    public WaitingEntity createWaiting(int waitingNumber, int waitingLeft, Long userId) {
        return WaitingEntity.builder()
                .userId(userId)
                .themeparkId(waiting.themeparkId)
                .waitingLeft(waitingLeft)
                .waitingNumber(waitingNumber)
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Waiting{

        @NotNull(message = "테마파크번호를 입력해주세요")
        private UUID themeparkId;

    }
}
