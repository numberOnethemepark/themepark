package com.business.themeparkservice.waiting.infastructure.kafka.service;

import com.business.themeparkservice.themepark.application.exception.ThemeparkExceptionCode;
import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
import com.business.themeparkservice.themepark.infastructure.persistence.themepark.ThemeparkJpaRepository;
import com.business.themeparkservice.waiting.application.exception.WaitingExceptionCode;
import com.business.themeparkservice.waiting.domain.entity.WaitingEntity;
import com.business.themeparkservice.waiting.domain.vo.WaitingStatus;
import com.business.themeparkservice.waiting.infastructure.kafka.dto.request.ReqSlackPostDTOApiV1;
import com.business.themeparkservice.waiting.infastructure.persistence.waiting.WaitingJpaRepository;
import com.github.themepark.common.application.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlackKafkaServiceApiV1 {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final WaitingJpaRepository waitingRepository;
    private final ThemeparkJpaRepository themeparkRepository;

    @Transactional(readOnly = true)
    public void getCallById(UUID id) {

        WaitingEntity waitingEntity =
                waitingRepository.findByIdAndWaitingStatus(id, WaitingStatus.WAITING)
                        .orElseThrow(() -> new CustomException(WaitingExceptionCode.WAITING_NOT_FOUND));

        ReqSlackPostDTOApiV1 request =  new ReqSlackPostDTOApiV1();

        String message = createCallMessage(waitingEntity);
        request.createslack(message);

        kafkaTemplate.send("slack-send",request);
    }

    private String createCallMessage(WaitingEntity waitingEntity) {
        ThemeparkEntity themeparkEntity =
                themeparkRepository.findById(waitingEntity.getThemeparkId()).orElseThrow(
                        ()->new CustomException(ThemeparkExceptionCode.THEMEPARK_NOT_FOUND));

        return themeparkEntity.getName() + " 대기번호" + waitingEntity.getWaitingNumber();
    }
}