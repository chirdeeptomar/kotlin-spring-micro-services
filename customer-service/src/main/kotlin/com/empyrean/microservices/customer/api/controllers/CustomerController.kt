package com.empyrean.microservices.customer.api.controllers

import com.empyrean.microservices.customer.api.requests.CreateCustomerRequest
import com.empyrean.microservices.customer.api.requests.UpdateCustomerRequest
import com.empyrean.microservices.customer.api.responses.CustomerResponse
import com.empyrean.microservices.customer.entity.Customer
import com.empyrean.microservices.customer.repository.CustomerRepository
import com.empyrean.microservices.customer.utils.createUrl
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/customers")
class CustomerController(val customerRepository: CustomerRepository) {

    @GetMapping
    fun index() = customerRepository.findAll().map { (name, email, id) -> CustomerResponse(id, name, email) }


    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): ResponseEntity<CustomerResponse> {
        return customerRepository.findById(id)
                .map { ok().body(CustomerResponse(it.id, it.name, it.email)) }
                .orElseGet { notFound().build() }
    }

    @PostMapping
    fun create(@RequestBody request: CreateCustomerRequest): ResponseEntity<CustomerResponse> {
        val saved = customerRepository.save(Customer(request.name, request.email))
        return created(createUrl(saved.id)).body(CustomerResponse(saved.id, saved.name, saved.email))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: UpdateCustomerRequest): ResponseEntity<Customer> {
        val customer = customerRepository.findById(id).orElse(null) ?: return notFound().build()
        val updatedCustomer = customer.copy(name = request.name, email = request.email)
        customerRepository.save(updatedCustomer)
        return ok(updatedCustomer)
    }
}