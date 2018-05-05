package com.empyrean.microservices.shoppingcart.config

import com.empyrean.microservices.shoppingcart.api.requests.CartItem
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
    fun reactiveRedisTemplate(redisConnectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, Array<CartItem>> {
        val serializer = Jackson2JsonRedisSerializer(Array<CartItem>::class.java)
        val builder = RedisSerializationContext.newSerializationContext<String, Array<CartItem>>(StringRedisSerializer())
        val serializationContext = builder.value(serializer).build()
        return ReactiveRedisTemplate(redisConnectionFactory, serializationContext)
    }

    @Bean
    fun reactiveRedisConnection(redisConnectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisConnection {
        return redisConnectionFactory.reactiveConnection
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
