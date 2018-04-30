package com.empyrean.microservices.web.model

import java.util.*

data class Customer(val name: String,
                    val email: String,
                    val id: UUID = UUID.randomUUID())