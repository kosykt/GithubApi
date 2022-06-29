package com.example.data.network

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitServiceTest : MockWebServerTest() {

    private lateinit var service: RetrofitService

    @Before
    fun setup() {
        val baseUrl = mockWebServer.url("/")
        service = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    @Test
    fun should_return_not_null_users_data() {
        enqueue("response_users.json")
        runBlocking {
            val apiResponse = service.getUsers()
            Assert.assertNotNull("response is null", apiResponse)
        }
    }

    @Test
    fun should_return_not_empty_users_data() {
        enqueue("response_users.json")
        runBlocking {
            val apiResponse = service.getUsers()
            Assert.assertTrue("response is empty", apiResponse.body()!!.isNotEmpty())
        }
    }

    @Test
    fun should_return_correct_id_users_data() {
        enqueue("response_users.json")
        runBlocking {
            val apiResponse = service.getUsers()
            val expected = "1"
            val actual = apiResponse.body()!![0].id
            Assert.assertEquals("response id is not correct", expected, actual)
        }
    }
}