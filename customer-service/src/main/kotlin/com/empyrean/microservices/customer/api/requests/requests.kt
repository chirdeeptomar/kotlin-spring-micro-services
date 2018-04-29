package com.empyrean.microservices.customer.api.requests

import java.util.*

data class CreateCustomerRequest(val name: String, val email: String)

data class UpdateCustomerRequest(val id: UUID, val name: String, val email: String)
