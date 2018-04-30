package com.empyrean.microservices.product

import com.empyrean.microservices.product.entity.Product
import com.empyrean.microservices.product.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean

@EnableEurekaClient
@SpringBootApplication
class ProductApplication {

    private val log = LoggerFactory.getLogger(ProductApplication::class.java)

    @Bean
    fun init(repository: ProductRepository) = CommandLineRunner {
        // save a couple of products
        repository.save(Product("Jack", "Casual trousers for men", 100.0, arrayOf("Trousers", "Men", "Casual")))
        repository.save(Product("Chloe", "Formal shirts for women", 50.0, arrayOf("Shirts", "Women", "Office")))
        repository.save(Product("David", "Polo Shirts for men", 1.0))
        repository.save(Product("Michelle", "Polo Shirts for women", 80.0))

        // fetch all products
        log.info("Products found with findAll():")
        log.info("-------------------------------")
        repository.findAll().forEach { log.info(it.toString()) }
        log.info("")
    }
}

fun main(args: Array<String>) {
    runApplication<ProductApplication>(*args)
}