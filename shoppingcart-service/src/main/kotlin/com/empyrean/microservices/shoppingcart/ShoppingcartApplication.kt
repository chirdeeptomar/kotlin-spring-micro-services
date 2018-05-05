package com.empyrean.microservices.shoppingcart

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class ShoppingcartApplication

fun main(args: Array<String>) {
    runApplication<ShoppingcartApplication>(*args)
}
