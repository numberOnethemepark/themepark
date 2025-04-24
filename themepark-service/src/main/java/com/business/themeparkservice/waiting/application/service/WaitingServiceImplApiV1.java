package com.business.themeparkservice.waiting.application.service;

import com.business.themeparkservice.themepark.application.exception.ThemeparkExceptionCode;
import com.business.themeparkservice.themepark.infastructure.persistence.themepark.ThemeparkJpaRepository;
import com.business.themeparkservice.waiting.application.dto.request.ReqWaitingPostDTOApiV1;
import com.business.themeparkservice.waiting.application.dto.response.*;
import com.business.themeparkservice.waiting.application.exception.WaitingExceptionCode;
import com.business.themeparkservice.waiting.domain.entity.WaitingEntity;
import com.business.themeparkservice.waiting.domain.entity.WaitingRedisEntity;
import com.business.themeparkservice.waiting.domain.vo.WaitingStatus;
import com.business.themeparkservice.waiting.infastructure.persistence.waiting.WaitingJpaRepository;
import com.business.themeparkservice.waiting.infastructure.redis.WaitingCrudRedisRepository;
import com.github.themepark.common.application.exception.CustomException;
import com.querydsl.core.types.Predicate;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class WaitingServiceImplApiV1 implements WaitingServiceApiV1{

    private final WaitingJpaRepository waitingRepository;

    private final WaitingCrudRedisRepository waitingRedisRepository;

    private final ThemeparkJpaRepository themeparkRepository;

    private final RedissonClient redissonClient;

    @Retryable(
            value = { org.springframework.dao.CannotAcquireLockException.class, org.postgresql.util.PSQLException.class },
            maxAttempts = 5,
            backoff = @Backoff(delay = 400, multiplier = 2)
    )
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public ResWaitingPostDTOApiV1 postBy(ReqWaitingPostDTOApiV1 reqDto,Long userId) {
        UUID themeparkId = reqDto.getWaiting().getThemeparkId();
        ThemeparkChecking(themeparkId);
//        WaitingChecking(reqDto,userId);

        RLock lock = redissonClient.getLock("lock:waiting:themeparkId:" + themeparkId);
        int waitingNumber = 1;
        int waitingLeft = 0;

        try{
            boolean lockChecking = lock.tryLock(5, TimeUnit.SECONDS);

            if(!lockChecking){
                throw new CustomException(WaitingExceptionCode.WAITING_LOCK_FAILED);
            }

            WaitingRedisEntity waitingRedisEntity =
                    waitingRedisRepository.findById(String.valueOf(themeparkId))
                            .orElse(null);

            if (waitingRedisEntity != null) {
                waitingLeft = waitingRedisEntity.getWaitingLeft()+1;
                waitingNumber = waitingRedisEntity.getWaitingNumber()+1;
            }else {
                WaitingEntity waitingInfo = waitingRepository.findLastWaitingNumber(themeparkId, WaitingStatus.WAITING)
                        .orElse(null);

                if (waitingInfo != null) {
                    waitingLeft = waitingInfo.getWaitingLeft()+1;
                    waitingNumber = waitingInfo.getWaitingNumber()+1;
                }
            }

            waitingRedisRepository.save(WaitingRedisEntity.builder()
                    .id(String.valueOf(themeparkId))
                    .waitingNumber(waitingNumber)
                    .waitingLeft(waitingLeft)
                    .build());

            WaitingEntity waitingEntity = reqDto.createWaiting(waitingNumber,waitingLeft,userId);

            waitingRepository.save(waitingEntity);

            return ResWaitingPostDTOApiV1.of(waitingEntity);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;
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
    public void deleteBy(UUID id,Long userId) {
        WaitingEntity waitingEntity = findByIdAndStatus(id,WaitingStatus.CANCELLED);
        waitingEntity.deletedBy(userId);
    }

    private WaitingEntity findByIdAndStatus(UUID id, WaitingStatus waitingStatus) {
        return waitingRepository.findByIdAndWaitingStatus(id,waitingStatus)
                .orElseThrow(() -> new CustomException(WaitingExceptionCode.WAITING_NOT_FOUND));
    }


    private void WaitingChecking(ReqWaitingPostDTOApiV1 reqDto, Long userId) {
        if(waitingRepository.countByThemeparkIdAndUserIdAndWaitingStatus(
                reqDto.getWaiting().getThemeparkId(),userId,WaitingStatus.WAITING)!= 0){
            throw new CustomException(WaitingExceptionCode.WAITING_DUPLICATE);
        }
    }

    private void ThemeparkChecking(@NotNull(message = "테마파크번호를 입력해주세요") UUID themeparkId) {
        themeparkRepository.findById(themeparkId).orElseThrow(
                ()->new CustomException(ThemeparkExceptionCode.THEMEPARK_NOT_FOUND));
    }
}
