package com.java.seed.user_order;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

interface UserOrderRepository extends Repository<UserOrder, UUID> {

    List<UserOrder> findAll();

    UserOrder save(UserOrder entity);

}
