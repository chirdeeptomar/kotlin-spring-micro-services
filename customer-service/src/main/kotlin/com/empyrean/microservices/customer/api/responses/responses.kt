package com.empyrean.microservices.customer.api.responses

import java.util.*

data class CustomerResponse(val id: UUID, val name: String, val email: String)