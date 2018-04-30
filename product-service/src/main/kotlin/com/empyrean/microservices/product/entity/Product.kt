package com.empyrean.microservices.product.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class Product(val name: String,
                   val description: String,
                   val price: Double,
                   val categories: Array<String>? = emptyArray(),
                   @Id @GeneratedValue(strategy = GenerationType.AUTO)
                   val id: UUID = UUID.randomUUID())