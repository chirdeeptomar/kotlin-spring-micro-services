package com.empyrean.microservices.product.repository

import com.empyrean.microservices.product.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductRepository : JpaRepository<Product, UUID>