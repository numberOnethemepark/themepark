package com.sparta.orderservice.application.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResOrdersGetByIdDtoApiV1 {

    public static ResOrdersGetByIdDtoApiV1 of(Integer id, int page, int size) {
        return ResOrdersGetByIdDtoApiV1.builder()
                .orders(Orders.from(id,page,size))
                .build();
    }

    @Valid
    @NotNull(message = "")
    private Orders orders;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Orders{
        List<ResOrderGetDtoApiV1.Order> ordersList;

        public static Orders from(Integer id, int page, int size) {
            return Orders.builder()
                    .build();
        }
    }
}
