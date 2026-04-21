package com.java.seed.user_order;

import com.java.seed.user_order.events.UserOrderEvent;
import com.java.seed.user_order.events.UserOrderEventKey;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserOrderListener {

    private final UserOrderService userOrderService;
    @KafkaListener
    void listen(ConsumerRecord<UserOrderEventKey, UserOrderEvent> consumerRecord) {
        userOrderService.process(consumerRecord.value());
    }
}
