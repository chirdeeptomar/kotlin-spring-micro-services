package com.empyrean.microservices.customer.api.controllers

import com.empyrean.microservices.customer.entity.Customer
import com.empyrean.microservices.customer.repository.CustomerRepository
import com.empyrean.microservices.customer.api.requests.CreateCustomerRequest
import com.empyrean.microservices.customer.api.requests.UpdateCustomerRequest
import com.empyrean.microservices.customer.api.responses.CustomerResponse
import com.empyrean.microservices.customer.utils.createUrl
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/customers")
class CustomerController(val customerRepository: CustomerRepository) {

    @HystrixCommand(fallbackMethod = "getAll")
    @GetMapping
    fun index() = customerRepository.findAll().map { (name, email, id) -> CustomerResponse(id, name, email) }


    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): ResponseEntity<CustomerResponse?> {
        val customer = customerRepository.findById(id).orElse(null) ?: return notFound().build()
        val response = CustomerResponse(customer.id, customer.name, customer.email)
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun create(@RequestBody request: CreateCustomerRequest): ResponseEntity<CustomerResponse>? {
        val customer = Customer(request.name, request.email)
        val saved = customerRepository.save(customer)
        return ResponseEntity.created(createUrl(saved.id)).body(CustomerResponse(saved.id, saved.name, saved.email))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: UpdateCustomerRequest): ResponseEntity<Customer> {
        val customer = customerRepository.findById(id).orElse(null) ?: return notFound().build()
        val updatedCustomer = customer.copy(name = request.name, email = request.email)
        customerRepository.save(updatedCustomer)
        return ResponseEntity.ok(updatedCustomer)
    }

    fun getAll(): List<CustomerResponse> {
        return listOf(CustomerResponse(UUID.randomUUID(), "Test Customer", "customer@test.com"))
    }
}