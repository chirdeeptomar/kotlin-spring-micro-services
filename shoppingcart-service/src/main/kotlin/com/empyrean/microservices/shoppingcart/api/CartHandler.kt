package com.empyrean.microservices.shoppingcart.api

import com.empyrean.microservices.shoppingcart.api.requests.CartItem
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.bodyToFlux
import reactor.core.publisher.Mono

@Component
class CartHandler(private val redisOperations: ReactiveRedisTemplate<String, Array<CartItem>>) {
    fun save(req: ServerRequest): Mono<Void> {
        val customerId = req.pathVariable("customerId")
        req.bodyToFlux<CartItem>()
                .collectList()
                .doOnSuccess { data -> save(customerId, data.toTypedArray()) }
                .subscribe { }
        return Mono.empty()
    }

    fun get(req: ServerRequest): Mono<Array<CartItem>> {
        return redisOperations.opsForValue()
                .get(req.pathVariable("customerId"))
    }

    private fun save(customerId: String, items: Array<CartItem>) {
        redisOperations.opsForValue().set(customerId, items).subscribe()
    }
}