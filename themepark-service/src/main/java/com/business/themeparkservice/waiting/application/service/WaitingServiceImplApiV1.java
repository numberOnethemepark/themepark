package com.business.themeparkservice.waiting.application.service;

import com.business.themeparkservice.themepark.application.exception.ThemeparkExceptionCode;
import com.business.themeparkservice.themepark.infastructure.persistence.themepark.ThemeparkJpaRepository;
import com.business.themeparkservice.waiting.application.dto.request.ReqWaitingPostDTOApiV1;
import com.business.themeparkservice.waiting.application.dto.response.*;
import com.business.themeparkservice.waiting.application.exception.WaitingExceptionCode;
import com.business.themeparkservice.waiting.domain.entity.WaitingEntity;
import com.business.themeparkservice.waiting.domain.vo.WaitingStatus;
import com.business.themeparkservice.waiting.infastructure.persistence.waiting.WaitingJpaRepository;
import com.github.themepark.common.application.exception.CustomException;
import com.querydsl.core.types.Predicate;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        int waitingLeft = waitingRepository.countByThemeparkId(reqDto.getWaiting().getThemeparkId());

        int waitingNumber=waitingRepository.findLastWaitingNumber(reqDto.getWaiting().getThemeparkId())+1;

        WaitingEntity waitingEntity = reqDto.createWaiting(waitingNumber,waitingLeft);
        waitingRepository.save(waitingEntity);

        return ResWaitingPostDTOApiV1.of(waitingEntity);
    }

    @Override
    public ResWaitingGetByIdDTOApiV1 getBy(UUID id) {
        WaitingEntity waitingEntity = findByIdAndStatus(id,WaitingStatus.WAITING);
        int waitingLeft = waitingRepository
                .checkingMyWaitingLeft(waitingEntity.getWaitingNumber(),waitingEntity.getThemeparkId());

        waitingEntity.updateWaitingLeft(waitingLeft);

        return ResWaitingGetByIdDTOApiV1.of(waitingEntity);
    }

    @Override
    public ResWaitingGetDTOApiV1 getBy(Predicate predicate, Pageable pageable) {
        Page<WaitingEntity> waitingEntityPage = waitingRepository.findAll(predicate,pageable);
        return ResWaitingGetDTOApiV1.of(waitingEntityPage);
    }

    @Override
    public ResWaitingPostDoneDTOApiV1 postDoneBy(UUID id) {
        WaitingEntity waitingEntity = findByIdAndStatus(id,WaitingStatus.WAITING);
        waitingEntity.postDone();
        return ResWaitingPostDoneDTOApiV1.of(waitingEntity);
    }

    @Override
    public ResWaitingPostCancelDTOApiV1 postCancelBy(UUID id) {
        WaitingEntity waitingEntity = findByIdAndStatus(id,WaitingStatus.WAITING);
        waitingEntity.postCancel();
        return ResWaitingPostCancelDTOApiV1.of(waitingEntity);
    }

    @Override
    public void deleteBy(UUID id) {
        WaitingEntity waitingEntity = findByIdAndStatus(id,WaitingStatus.CANCELLED);
        waitingEntity.deletedBy(1L);
    }

    private WaitingEntity findByIdAndStatus(UUID id, WaitingStatus waitingStatus) {
        return waitingRepository.findByIdAndWaitingStatus(id,waitingStatus)
                .orElseThrow(() -> new CustomException(WaitingExceptionCode.WAITING_NOT_FOUND));
    }


    private int WaitingChecking(ReqWaitingPostDTOApiV1 reqDto) {
        return waitingRepository
                .countByThemeparkIdAndUserId(reqDto.getWaiting().getThemeparkId(),reqDto.getWaiting().getUserId());
    }

    private void ThemeparkChecking(@NotNull(message = "테마파크번호를 입력해주세요") UUID themeparkId) {
        themeparkRepository.findById(themeparkId).orElseThrow(
                ()->new CustomException(ThemeparkExceptionCode.THEMEPARK_NOT_FOUND));
    }
}
