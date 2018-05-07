package com.empyrean.microservices.order.api.responses

import com.empyrean.microservices.order.model.Order

data class OrderPlacedResponse(val message: String, val order: Order)
