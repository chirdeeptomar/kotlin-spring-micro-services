package com.empyrean.microservices.order.repository

import com.empyrean.microservices.order.model.Order
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface OrderRepository : ReactiveMongoRepository<Order, String>
