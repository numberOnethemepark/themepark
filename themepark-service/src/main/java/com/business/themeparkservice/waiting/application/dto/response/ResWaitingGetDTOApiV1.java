package com.business.themeparkservice.waiting.application.dto.response;

import com.business.themeparkservice.waiting.domain.entity.WaitingEntity;
import com.business.themeparkservice.waiting.domain.vo.WaitingStatus;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResWaitingGetDTOApiV1 {
    private WaitingPage waitingPage;

    public static ResWaitingGetDTOApiV1 of(Page<WaitingEntity> tempWaitingPage) {
        return ResWaitingGetDTOApiV1.builder()
                .waitingPage(new WaitingPage(tempWaitingPage))
                .build();
    }

    @Getter
    @ToString
    public static class WaitingPage extends PagedModel<WaitingPage.Waiting> {
        public WaitingPage(Page<WaitingEntity> waitingEntityPage) {
            super(
                    new PageImpl<>(
                            Waiting.from(waitingEntityPage.getContent()),
                            waitingEntityPage.getPageable(),
                            waitingEntityPage.getTotalElements()

                    )
            );
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

            public static List<Waiting> from(List<WaitingEntity> waitingEntityList) {
                return waitingEntityList.stream()
                        .map(Waiting::from)
                        .toList();
            }

            public static Waiting from(WaitingEntity entity) {
                return Waiting.builder()
                        .id(UUID.fromString("f4a49e7d-3baf-478f-bb36-d73b66330f79"))
                        .userId(3)
                        .themeparkId(UUID.fromString("f5e49e7d-3baf-478f-bb36-d73b66330f79"))
                        .waitingNumber(2)
                        .waitingLeft(1)
                        .status(WaitingStatus.WAITING)
                        .build();
            }
        }
    }
}
