package com.empyrean.microservices.customer.repository

import com.empyrean.microservices.customer.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CustomerRepository : JpaRepository<Customer, UUID>