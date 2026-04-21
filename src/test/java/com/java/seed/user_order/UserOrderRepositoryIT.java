package com.java.seed.user_order;

import com.java.seed.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserOrderRepositoryIT extends IntegrationTest {

    @Autowired
    private UserOrderRepository repository;

    @Test
    void findAll() {
        // given
        // persist

        // when
        List<UserOrder> result = repository.findAll();

        // then
        UserOrder expectedResult = null;

    }

    @Test
    void save() {
    }
}