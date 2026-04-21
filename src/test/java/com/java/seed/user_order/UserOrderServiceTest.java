package com.java.seed.user_order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserOrderServiceTest {

    @Mock
    private UserOrderRepository repository;

    private UserOrderService testObj;

    @BeforeEach
    public void setup() {
        testObj = new UserOrderService(repository);
    }

    @Test
    void list() {
        // given
        List<UserOrder> mocked = List.of(new UserOrder(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()));

        given(repository.findAll()).willReturn(mocked);


        // when
        List<UserOrder> listFromDB = testObj.list();

        // then
        assertThat(listFromDB).containsAll(mocked);
        then(repository).should().findAll();
    }
}