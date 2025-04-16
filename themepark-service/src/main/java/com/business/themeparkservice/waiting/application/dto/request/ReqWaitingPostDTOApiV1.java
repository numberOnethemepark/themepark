package com.business.themeparkservice.waiting.application.dto.request;

import com.business.themeparkservice.waiting.domain.entity.WaitingEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
public class ReqWaitingPostDTOApiV1 {
    @Valid
    @NotNull(message = "대기정보를 입력해주세요")
    private Waiting waiting;

    public WaitingEntity createWaiting(int waitingNumber, int waitingLeft) {
        return WaitingEntity.builder()
                .userId(waiting.userId)
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
        @NotNull(message = "회원번호를 입력해주세요")
        private Integer userId;

        @NotNull(message = "테마파크번호를 입력해주세요")
        private UUID themeparkId;

    }
}
