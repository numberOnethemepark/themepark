package com.business.themeparkservice.waiting.application.service;

import com.business.themeparkservice.themepark.infastructure.persistence.themepark.ThemeparkJpaRepository;
import com.business.themeparkservice.waiting.application.dto.request.ReqWaitingPostDTOApiV1;
import com.business.themeparkservice.waiting.application.dto.response.ResWaitingPostDTOApiV1;
import com.business.themeparkservice.waiting.domain.entity.WaitingEntity;
import com.business.themeparkservice.waiting.infastructure.persistence.waiting.WaitingJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class WaitingServiceImplApiV1 implements WaitingServiceApiV1{

    private final WaitingJpaRepository waitingRepository;

    private final ThemeparkJpaRepository themeparkRepository;

    @Override
    public ResWaitingPostDTOApiV1 postBy(ReqWaitingPostDTOApiV1 reqDto) {
        ThemeparkChecking(reqDto.getWaiting().getThemeparkId());

        if(WaitingChecking(reqDto) == 1){
            throw new RuntimeException("대기정보가 존재합니다.");
        }

        //대기번호 생성
        int waitingNumber = waitingRepository.countByThemeparkId(reqDto.getWaiting().getThemeparkId()) + 1;
        //남은 대기자 수
        int waitingLeft = waitingNumber - 1;

        WaitingEntity waitingEntity = reqDto.createWaiting(waitingNumber,waitingLeft);
        waitingRepository.save(waitingEntity);

        return ResWaitingPostDTOApiV1.of(waitingEntity);
    }

    private int WaitingChecking(ReqWaitingPostDTOApiV1 reqDto) {
        return waitingRepository
                .countByThemeparkIdAndUserId(reqDto.getWaiting().getThemeparkId(),reqDto.getWaiting().getUserId());
    }

    private void ThemeparkChecking(@NotNull(message = "테마파크번호를 입력해주세요") UUID themeparkId) {
        themeparkRepository.findById(themeparkId).orElseThrow(
                ()->new EntityNotFoundException("Themepark not found"));
    }
}
