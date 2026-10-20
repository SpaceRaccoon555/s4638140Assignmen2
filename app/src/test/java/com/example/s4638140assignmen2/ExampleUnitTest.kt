package com.example.s4638140assignmen2

import com.example.s4638140assignmen2.data.LoginRequest
import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun loginRequest_storesUsernameCorrectly() {
        val request = LoginRequest(
            username = "12345678",
            password = "Alex"
        )

        assertEquals("12345678", request.username)
    }

    @Test
    fun loginRequest_storesPasswordCorrectly() {
        val request = LoginRequest(
            username = "12345678",
            password = "Alex"
        )

        assertEquals("Alex", request.password)
    }
}