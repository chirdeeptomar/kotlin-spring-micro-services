package com.empyrean.microservices.customer.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class Customer(val name: String,
                    val email: String,
                    @Id @GeneratedValue(strategy = GenerationType.AUTO)
                    val id: UUID = UUID.randomUUID())