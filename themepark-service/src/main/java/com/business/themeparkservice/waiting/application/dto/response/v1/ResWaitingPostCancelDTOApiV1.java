package com.business.themeparkservice.waiting.application.dto.response.v1;

import com.business.themeparkservice.waiting.domain.entity.WaitingEntity;
import com.business.themeparkservice.waiting.domain.vo.WaitingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResWaitingPostCancelDTOApiV1 {
    private Waiting waiting;

    public static ResWaitingPostCancelDTOApiV1 of(WaitingEntity waitingEntity) {
        return ResWaitingPostCancelDTOApiV1.builder()
                .waiting(Waiting.from(waitingEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Waiting{
        private UUID id;
        private Long userId;
        private UUID themeparkId;
        private Integer waitingNumber;
        private Integer waitingLeft;
        private WaitingStatus status;

        public static Waiting from(WaitingEntity waitingEntity) {
            return Waiting.builder()
                    .id(waitingEntity.getId())
                    .userId(waitingEntity.getUserId())
                    .themeparkId(waitingEntity.getThemeparkId())
                    .waitingNumber(waitingEntity.getWaitingNumber())
                    .waitingLeft(waitingEntity.getWaitingLeft())
                    .status(waitingEntity.getWaitingStatus())
                    .build();
        }
    }
}