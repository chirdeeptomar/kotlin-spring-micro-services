package com.empyrean.microservices.order.api.controllers

import com.empyrean.microservices.order.api.errors.notFound
import com.empyrean.microservices.order.api.errors.serverError
import com.empyrean.microservices.order.api.responses.OrderPlacedResponse
import com.empyrean.microservices.order.dto.Cart
import com.empyrean.microservices.order.model.Order
import com.empyrean.microservices.order.model.OrderItem
import com.empyrean.microservices.order.repository.OrderRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/api/orders")
class OrderController(private val orderRepository: OrderRepository, private val webClient: WebClient) {

    @PostMapping(path = ["/customers/{customerId}/place"])
    fun create(@PathVariable customerId: String): Mono<ResponseEntity<OrderPlacedResponse>> {

        return webClient
                .get()
                .uri("/$customerId/cart", customerId)
                .exchange()
                .flatMap { it.bodyToMono(Cart::class.java) }
                .map { it.items.map { OrderItem(it.productId, it.quantity, it.price) } }
                .map { items -> Order(customerId, items, UUID.randomUUID().toString()) }
                .flatMap { orderRepository.save(it) }
                .map {
                    ResponseEntity.ok(OrderPlacedResponse("Order Placed", it))
                }
                .switchIfEmpty(notFound<OrderPlacedResponse>())
                .doOnError { serverError<OrderPlacedResponse>() }
    }
}

