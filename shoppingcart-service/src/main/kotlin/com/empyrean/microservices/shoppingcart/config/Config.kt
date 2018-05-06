package com.empyrean.microservices.shoppingcart.config

import com.empyrean.microservices.shoppingcart.api.requests.Cart
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.lettuce.core.support.RedisClientFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnection
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
class RedisConfig {

    @Bean
    fun reactiveRedisTemplate(connectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, Cart> {
        val serializer = Jackson2JsonRedisSerializer(Cart::class.java)
        val builder = RedisSerializationContext.newSerializationContext<String, Cart>(StringRedisSerializer())
        val serializationContext = builder.value(serializer).build()
        return ReactiveRedisTemplate(connectionFactory, serializationContext)
    }

    @Bean
    fun reactiveRedisConnection(connectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisConnection {
        return connectionFactory.reactiveConnection
    }

    @Bean
    fun redisConnectionFactory(): RedisClientFactoryBean {
        return RedisClientFactoryBean()
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper()
    }
}
