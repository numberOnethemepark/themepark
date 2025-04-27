//package com.business.themeparkservice.waiting.infastructure.feign.service;
//
//import com.business.themeparkservice.themepark.application.exception.ThemeparkExceptionCode;
//import com.business.themeparkservice.themepark.domain.entity.ThemeparkEntity;
//import com.business.themeparkservice.themepark.infastructure.persistence.themepark.ThemeparkJpaRepository;
//import com.business.themeparkservice.waiting.application.exception.WaitingExceptionCode;
//import com.business.themeparkservice.waiting.domain.entity.WaitingEntity;
//import com.business.themeparkservice.waiting.domain.vo.WaitingStatus;
//import com.business.themeparkservice.waiting.infastructure.feign.dto.request.ReqKafkaPostDTOApiV1;
//import com.business.themeparkservice.waiting.infastructure.feign.SlackFeignClientApiV1;
//import com.business.themeparkservice.waiting.infastructure.persistence.waiting.WaitingJpaRepository;
//import com.github.themepark.common.application.exception.CustomException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.UUID;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class SlackFeignClientServiceApiV1 {
//
//    private final SlackFeignClientApiV1 slackFeignClientApiV1;
//    private final WaitingJpaRepository waitingRepository;
//    private final ThemeparkJpaRepository themeparkRepository;
//
//
//    public void getCallById(UUID id) {
//
//        WaitingEntity waitingEntity =
//                waitingRepository.findByIdAndWaitingStatus(id, WaitingStatus.WAITING)
//                .orElseThrow(() -> new CustomException(WaitingExceptionCode.WAITING_NOT_FOUND));
//
//        ReqKafkaPostDTOApiV1 request =  new ReqKafkaPostDTOApiV1();
//
//        String message = createCallMessage(waitingEntity);
//        request.createslack(message);
//
//        slackFeignClientApiV1.postBy(request);
//    }
//
//    private String createCallMessage(WaitingEntity waitingEntity) {
//        ThemeparkEntity themeparkEntity =
//                themeparkRepository.findById(waitingEntity.getThemeparkId()).orElseThrow(
//                ()->new CustomException(ThemeparkExceptionCode.THEMEPARK_NOT_FOUND));
//
//        return themeparkEntity.getName() + " 대기번호" + waitingEntity.getWaitingNumber();
//    }
//}
