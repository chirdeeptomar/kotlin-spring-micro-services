package com.empyrean.microservices.shoppingcart.api

import com.empyrean.microservices.shoppingcart.api.requests.CartItem
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono


@Configuration
class Router(private val cartHandler: CartHandler, val mapper: ObjectMapper) {
    @Bean
    fun appRouter() = router {
        accept(MediaType.APPLICATION_JSON).nest {
            "/api/".nest {

                POST("/{customerId}/cart") {
                    cartHandler.save(it).subscribe()
                    val message = "{ \"message\": \"Cart Saved\"}"
                    val node = mapper.readValue(message, JsonNode::class.java)
                    ServerResponse
                            .created(it.uri())
                            .body(BodyInserters.fromObject(node))
                }

                GET("/{customerId}/cart") {
                    cartHandler.get(it)
                            .flatMap { data ->
                                ServerResponse
                                        .ok()
                                        .body(BodyInserters.fromPublisher(Mono.just(data), Array<CartItem>::class.java))
                            }
                            .switchIfEmpty(ServerResponse.notFound().build())
                }
            }
        }
    }
}
