package com.example.demo.app

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class HelloController {
    @Get("/hello")
    fun hello():String = "Hello world!"
}