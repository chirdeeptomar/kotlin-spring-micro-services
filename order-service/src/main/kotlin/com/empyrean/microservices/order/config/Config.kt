package com.empyrean.microservices.order.config


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class RedisConfig {

    @Bean
    fun webClient(): WebClient {
        return WebClient.create("http://192.168.2.82:8080/api/")
    }


    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper()
    }

}
