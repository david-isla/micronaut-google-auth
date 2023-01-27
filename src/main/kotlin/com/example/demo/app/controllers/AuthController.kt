package com.example.demo.app.controllers

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.cookie.Cookies
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import java.lang.Exception

@Controller("/auth")
class AuthController {

    @Secured(SecurityRule.IS_ANONYMOUS)
    @View("auth")
    @Get
    fun validate( cookies: Cookies ) : Map<String, Any> {
        val result = mutableMapOf<String, Any>()

        result["numCookies"] = cookies.count()

        val jwtCookie = cookies.findCookie("JWT")
        result["isJWTPresent"] = jwtCookie.isPresent

        if( jwtCookie.isPresent ) {
            val cookie = jwtCookie.get()
            val value = cookie.value

            result.putAll(verifyJWT(value))
        }


        return result
    }

    private fun verifyJWT(jwt: String) : Map<String, Any> {
        val result = mutableMapOf<String, Any>()

        val verifier = GoogleIdTokenVerifier.Builder(
            NetHttpTransport(),
            GsonFactory.getDefaultInstance()
        )
            .setAudience(listOf(CLIENT_ID))
            .build()

        try {
            result["JWTValid"] = true
            val idToken = verifier.verify(jwt)
            val payLoad = idToken.payload

            result["JWTUser"] = payLoad.subject
            result["JWTEmail"] = payLoad.email
        } catch (e: Exception) {
            result["JWTValid"] = false
        }


        return result
    }

    private companion object {
        private val CLIENT_ID = "611628571049-e348cp3d7kgn9ndkdkj77paj7usj5pu4.apps.googleusercontent.com"
        private val CLIENT_SECRET = "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"
    }
}