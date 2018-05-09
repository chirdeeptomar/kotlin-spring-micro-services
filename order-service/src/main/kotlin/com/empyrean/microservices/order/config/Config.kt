package com.empyrean.microservices.order.config


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class ServiceConfig(private val lbFunction: LoadBalancerExchangeFilterFunction) {

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder().filter(lbFunction).baseUrl("http://cart-micro-service/api/").build()
    }


    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper()
    }

}
