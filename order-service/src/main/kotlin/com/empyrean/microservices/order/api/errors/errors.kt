package com.empyrean.microservices.order.api.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reactor.core.publisher.toMono

fun <T> notFound() = ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .build<T>().toMono()


fun <T> serverError() = ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .build<T>().toMono()