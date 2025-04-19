package com.business.slackservice.domain.slack.vo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EventType {
    STOCK_OUT("재고 매진"),
    ORDER_COMPLETE("주문 완료"),
    WAITING_NEAR("입장 순서 임박"),
    WAITING_CALL("대기자 호출");

    private final String description;
}
