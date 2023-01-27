package com.example.demo.app.controllers

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View

@Controller("/auth")
class AuthController {

    @Secured(SecurityRule.IS_ANONYMOUS)
    @View("auth")
    @Get
    fun index(): Map<String, Any> = HashMap()

    private companion object {
        private val CLIENT_ID = "611628571049-e348cp3d7kgn9ndkdkj77paj7usj5pu4.apps.googleusercontent.com"
        private val CLIENT_SECRET = "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"
    }
}