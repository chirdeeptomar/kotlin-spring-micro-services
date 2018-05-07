package com.empyrean.microservices.order.dto

import org.joda.time.DateTime

data class Cart(val customerId: String = "", val items: List<CartItem> = emptyList(), val createdOn: String = DateTime.now().toLocalDateTime().toString())

data class CartItem(val productId: String = "", val quantity: Int = 0, val price: Float = 0.00F)
