package com.example.data.network

import com.example.data.network.model.RepoDTO
import com.example.data.network.model.RepoOwner
import com.example.data.network.model.UserDTO
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class NetworkRepositoryImplTest {

    private val retrofitService = mock<RetrofitService>()
    private val testUrl = "url"

    @After
    fun tearDown() {
        Mockito.reset(retrofitService)
    }

    @Test
    fun should_return_not_null_users_data() {
        val networkRepositoryImpl = NetworkRepositoryImpl(retrofitService)
        val testData = listOf(
            UserDTO(id = "1", login = "1", avatarUrl = "avatar_url"),
            UserDTO(id = "2", login = "2", avatarUrl = "avatar_url"),
            UserDTO(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(retrofitService.getUsers()).thenReturn(testData)
            Assert.assertNotNull(
                "Returned data is null",
                networkRepositoryImpl.getUsers()
            )
        }
    }

    @Test
    fun should_return_equals_users_data() {
        val networkRepositoryImpl = NetworkRepositoryImpl(retrofitService)
        val testData = listOf(
            UserDTO(id = "1", login = "1", avatarUrl = "avatar_url"),
            UserDTO(id = "2", login = "2", avatarUrl = "avatar_url"),
            UserDTO(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(retrofitService.getUsers()).thenReturn(testData)
            Assert.assertEquals(
                "Returned data is null",
                testData,
                networkRepositoryImpl.getUsers()
            )
        }
    }

    @Test
    fun should_return_not_equals_users_data() {
        val networkRepositoryImpl = NetworkRepositoryImpl(retrofitService)
        val testData = listOf(
            UserDTO(id = "1", login = "1", avatarUrl = "avatar_url"),
            UserDTO(id = "2", login = "2", avatarUrl = "avatar_url"),
            UserDTO(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(retrofitService.getUsers()).thenReturn(testData)
            Assert.assertNotEquals(
                "Returned data is null",
                testData,
                networkRepositoryImpl.getUsers().first()
            )
        }
    }

    @Test
    fun verify_users() {
        val networkRepositoryImpl = NetworkRepositoryImpl(retrofitService)
        runBlocking {
            networkRepositoryImpl.getUsers()
            Mockito.verify(retrofitService, Mockito.times(1)).getUsers()
            Mockito.verify(retrofitService, Mockito.times(0)).getRepos(testUrl)
        }
    }


    @Test
    fun should_return_not_null_repos_data() {
        val networkRepositoryImpl = NetworkRepositoryImpl(retrofitService)
        val testData = listOf(
            RepoDTO(id = "1", owner = RepoOwner(id = "1"), name = "name"),
            RepoDTO(id = "2", owner = RepoOwner(id = "2"), name = "name"),
            RepoDTO(id = "3", owner = RepoOwner(id = "3"), name = "name"),
        )
        runBlocking {
            Mockito.`when`(retrofitService.getRepos(testUrl)).thenReturn(testData)
            Assert.assertNotNull(
                "Returned data is null",
                networkRepositoryImpl.getRepos(testUrl)
            )
        }
    }

    @Test
    fun should_return_equals_repos_data() {
        val networkRepositoryImpl = NetworkRepositoryImpl(retrofitService)
        val testData = listOf(
            RepoDTO(id = "1", owner = RepoOwner(id = "1"), name = "name"),
            RepoDTO(id = "2", owner = RepoOwner(id = "2"), name = "name"),
            RepoDTO(id = "3", owner = RepoOwner(id = "3"), name = "name"),
        )
        runBlocking {
            Mockito.`when`(retrofitService.getRepos(testUrl)).thenReturn(testData)
            Assert.assertEquals(
                "Returned data is null",
                testData,
                networkRepositoryImpl.getRepos(testUrl)
            )
        }
    }

    @Test
    fun should_return_not_equals_repos_data() {
        val networkRepositoryImpl = NetworkRepositoryImpl(retrofitService)
        val testData = listOf(
            RepoDTO(id = "1", owner = RepoOwner(id = "1"), name = "name"),
            RepoDTO(id = "2", owner = RepoOwner(id = "2"), name = "name"),
            RepoDTO(id = "3", owner = RepoOwner(id = "3"), name = "name"),
        )
        runBlocking {
            Mockito.`when`(retrofitService.getRepos(testUrl)).thenReturn(testData)
            Assert.assertNotEquals(
                "Returned data is null",
                testData,
                networkRepositoryImpl.getRepos(testUrl).first()
            )
        }
    }

    @Test
    fun verify_repos() {
        val networkRepositoryImpl = NetworkRepositoryImpl(retrofitService)
        runBlocking {
            networkRepositoryImpl.getRepos(testUrl)
            Mockito.verify(retrofitService, Mockito.times(0)).getUsers()
            Mockito.verify(retrofitService, Mockito.times(1)).getRepos(testUrl)
        }
    }
}