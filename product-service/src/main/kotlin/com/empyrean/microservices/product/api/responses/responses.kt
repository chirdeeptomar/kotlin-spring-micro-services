package com.empyrean.microservices.product.api.responses

import java.util.*

data class ProductResponse(val id: UUID,
                           val name: String,
                           val email: String,
                           val price: Double,
                           val categories: Array<String>? = emptyArray())