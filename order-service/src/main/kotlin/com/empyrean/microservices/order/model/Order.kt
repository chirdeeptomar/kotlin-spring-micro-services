package com.empyrean.microservices.order.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class OrderItem(val productId: String = "", val quantity: Int = 0, val price: Float = 0F)

@Document
data class Order(val customerId: String = "", val items: List<OrderItem> = emptyList(), @Id val id: String = "")