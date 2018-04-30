package com.empyrean.microservices.web.controllers

import com.empyrean.microservices.web.model.Customer
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.client.RestTemplate
import java.util.*

@Controller
class HomeController(val restTemplate: RestTemplate) {

    @HystrixCommand(fallbackMethod = "getInMemoryCustomers")
    @RequestMapping("/")
    fun index(model: Model): String {
        val customers = restTemplate.getForEntity("http://customer-micro-service/api/customers", Array<Customer>::class.java)
        model.addAttribute("customers", customers.body)
        return "layout"
    }

    fun getInMemoryCustomers(model: Model): String {
        val c1 = Customer("John", "john@service.com", UUID.randomUUID())
        val c2 = Customer("Emily", "emily@service.com", UUID.randomUUID())
        val c3 = Customer("Mike", "mike@service.com", UUID.randomUUID())
        val c4 = Customer("Sharon", "sharon@service.com", UUID.randomUUID())
        model.addAttribute("customers", listOf(c1, c2, c3, c4))
        return "layout"
    }
}
