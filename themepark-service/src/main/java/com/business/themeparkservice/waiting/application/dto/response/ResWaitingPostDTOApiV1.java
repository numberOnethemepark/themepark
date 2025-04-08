package com.business.themeparkservice.waiting.application.dto.response;

import com.business.themeparkservice.waiting.application.dto.request.ReqWaitingPostDTOApiV1;
import com.business.themeparkservice.waiting.domain.vo.WaitingStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResWaitingPostDTOApiV1 {
    private Waiting waiting;

    public static ResWaitingPostDTOApiV1 of(@Valid ReqWaitingPostDTOApiV1 reqDto) {
        return ResWaitingPostDTOApiV1.builder()
                .waiting(Waiting.from(reqDto))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Waiting{
        private UUID id;
        private Integer userId;
        private UUID themeparkId;
        private Integer waitingNumber;
        private Integer waitingLeft;
        private WaitingStatus status;

        public static Waiting from(@Valid ReqWaitingPostDTOApiV1 reqDto) {
            return Waiting.builder()
                    .id(UUID.fromString("f5e49e7d-3baf-478f-bb36-d73b66330f79"))
                    .userId(reqDto.getWaiting().getUserId())
                    .themeparkId(reqDto.getWaiting().getThemeparkId())
                    .waitingNumber(3)
                    .waitingLeft(2)
                    .status(WaitingStatus.WAITING)
                    .build();
        }
    }

}
