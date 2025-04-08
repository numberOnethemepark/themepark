package com.business.themeparkservice.waiting.application.dto.response;

import com.business.themeparkservice.waiting.domain.vo.WaitingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResWaitingPostDoneDTOApiV1 {
    private Waiting waiting;

    public static ResWaitingPostDoneDTOApiV1 of(UUID id) {
        return ResWaitingPostDoneDTOApiV1.builder()
                .waiting(Waiting.from(id))
                .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Waiting{
        private UUID id;
        private Integer userId;
        private UUID themeparkId;
        private Integer waitingNumber;
        private Integer waitingLeft;
        private WaitingStatus status;

        public static Waiting from(UUID id) {
            return Waiting.builder()
                    .id(id)
                    .userId(3)
                    .themeparkId(UUID.fromString("f5e49e7d-3baf-478f-bb36-d73b66330f79"))
                    .waitingNumber(2)
                    .waitingLeft(1)
                    .status(WaitingStatus.DONE)
                    .build();
        }

    }
}
