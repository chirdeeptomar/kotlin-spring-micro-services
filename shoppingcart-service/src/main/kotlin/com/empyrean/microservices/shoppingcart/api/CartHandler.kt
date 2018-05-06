package com.empyrean.microservices.shoppingcart.api

import com.empyrean.microservices.shoppingcart.api.requests.Cart
import com.empyrean.microservices.shoppingcart.api.requests.CartItem
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.bodyToFlux
import reactor.core.publisher.Mono

@Component
class CartHandler(private val redisOperations: ReactiveRedisTemplate<String, Cart>) {

    fun save(req: ServerRequest): Mono<Boolean> {
        val customerId = req.pathVariable("customerId")
        return req.bodyToFlux<CartItem>()
                .collectList()
                .doOnSuccess { data -> save(customerId, Cart(customerId, data)) }
                .thenReturn(true)
    }

    fun get(req: ServerRequest): Mono<Cart> {
        return redisOperations
                .opsForValue()
                .get(req.pathVariable("customerId"))
    }

    private fun save(customerId: String, cart: Cart) {
        redisOperations
                .opsForValue()
                .set(customerId, cart)
                .subscribe { d -> println(d) }
    }

}