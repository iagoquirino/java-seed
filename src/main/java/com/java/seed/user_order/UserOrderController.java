package com.java.seed.user_order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class UserOrderController {

    private final UserOrderService service;

    List<UserOrderResponse> list() {
        return service.list()
                .stream()
                .map(entity -> new UserOrderResponse(entity.id(), entity.userId(), entity.orderId()))
                .toList();
    }

    record UserOrderResponse(UUID id, UUID userId, UUID orderId){};
}
