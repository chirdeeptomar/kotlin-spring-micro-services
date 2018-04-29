package com.empyrean.microservices.customer.utils

import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*

fun createUrl(id: UUID): URI = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(id).toUri()
