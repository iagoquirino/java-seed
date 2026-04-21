package com.java.seed.user_order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "user_order")
public record UserOrder (@Id UUID id, UUID userId, UUID orderId){
}
