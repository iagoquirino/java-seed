package com.java.seed.shared;

import com.java.seed.user_order.UserOrder;
import com.java.seed.user_order.events.UserOrderEvent;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.UUID;

@UtilityClass
public class TestUtils {

    public class UserOrderInformation {
        public static UserOrder givenUserOrder() {
            return UserOrder.builder()
                    .id(UUID.randomUUID())
                    .userId(UUID.randomUUID())
                    .orderId(UUID.randomUUID())
                    .build();
        }

        public static UserOrderEvent givenUserOrderEvent() {
            return UserOrderEvent.newBuilder()
                    .setUserId(UUID.randomUUID())
                    .setOrderId(UUID.randomUUID())
                    .setName("John Doe")
                    .setCreatedAt(Instant.now())
                    .build();
        }
    }


}
