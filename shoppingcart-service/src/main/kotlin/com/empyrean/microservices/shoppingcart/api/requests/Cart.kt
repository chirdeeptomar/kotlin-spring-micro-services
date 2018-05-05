package com.empyrean.microservices.shoppingcart.api.requests

data class CartItem(val productId: String = "", val quantity: Int = 0)
