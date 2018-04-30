package com.empyrean.microservices.product.api.controllers

import com.empyrean.microservices.product.api.responses.ProductResponse
import com.empyrean.microservices.product.repository.ProductRepository
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/api/products")
class ProductController(val productRepository: ProductRepository) {

    @GetMapping
    fun index() = productRepository.findAll().map {
        ProductResponse(it.id, it.name, it.description, it.price, it.categories)
    }


    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): ResponseEntity<ProductResponse> {
        return productRepository.findById(id)
                .map { ok().body(ProductResponse(it.id, it.name, it.description, it.price, it.categories)) }
                .orElseGet { notFound().build() }
    }
}