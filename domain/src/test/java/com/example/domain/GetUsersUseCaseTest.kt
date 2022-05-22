package com.example.domain


import com.example.domain.models.DomainUserModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetUsersUseCaseTest {

    private val domainRepository = mock<DomainRepository>()
    private val testNetworkAvailable = true
    private val testNetworkLost = false

    @After
    fun tearDown() {
        Mockito.reset(domainRepository)
    }

    @Test
    fun should_return_not_null_data_if_network_is_available() {
        val useCase = GetUsersUseCase(domainRepository)
        val testData = listOf(
            DomainUserModel(id = "1", login = "1", avatarUrl = "avatar_url"),
            DomainUserModel(id = "2", login = "2", avatarUrl = "avatar_url"),
            DomainUserModel(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(domainRepository.getUsersFromNetwork()).thenReturn(testData)
            Assert.assertNotNull(
                "Returned data is null",
                useCase.execute(testNetworkAvailable)
            )
        }
    }

    @Test
    fun should_return_equals_data_if_network_is_available() {
        val useCase = GetUsersUseCase(domainRepository)
        val testData = listOf(
            DomainUserModel(id = "1", login = "1", avatarUrl = "avatar_url"),
            DomainUserModel(id = "2", login = "2", avatarUrl = "avatar_url"),
            DomainUserModel(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(domainRepository.getUsersFromNetwork()).thenReturn(testData)
            Assert.assertEquals(
                "Returned data is not equals",
                testData,
                useCase.execute(testNetworkAvailable)
            )
        }
    }

    @Test
    fun should_return_not_equals_data_if_network_is_available() {
        val useCase = GetUsersUseCase(domainRepository)
        val testData = listOf(
            DomainUserModel(id = "1", login = "1", avatarUrl = "avatar_url"),
            DomainUserModel(id = "2", login = "2", avatarUrl = "avatar_url"),
            DomainUserModel(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(domainRepository.getUsersFromNetwork()).thenReturn(testData)
            Assert.assertNotEquals(
                "Returned data is equals",
                testData,
                useCase.execute(testNetworkAvailable).first()
            )
        }
    }

    @Test
    fun verify_if_network_is_available() {
        val useCase = GetUsersUseCase(domainRepository)
        runBlocking {
            useCase.execute(testNetworkAvailable)
            Mockito.verify(domainRepository, Mockito.times(1)).getUsersFromNetwork()
            Mockito.verify(domainRepository, Mockito.times(0)).getUsersFromDatabase()
            Mockito.verify(domainRepository, Mockito.times(0)).getReposFromNetwork("test")
            Mockito.verify(domainRepository, Mockito.times(0)).getReposFromDatabase("test")
        }
    }

    @Test
    fun should_return_not_null_data_if_network_is_lost() {
        val useCase = GetUsersUseCase(domainRepository)
        val testData = listOf(
            DomainUserModel(id = "1", login = "1", avatarUrl = "avatar_url"),
            DomainUserModel(id = "2", login = "2", avatarUrl = "avatar_url"),
            DomainUserModel(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(domainRepository.getUsersFromDatabase()).thenReturn(testData)
            Assert.assertNotNull(
                "Returned data is null",
                useCase.execute(testNetworkLost).first()
            )
        }
    }

    @Test
    fun should_return_equals_data_if_network_is_lost() {
        val useCase = GetUsersUseCase(domainRepository)
        val testData = listOf(
            DomainUserModel(id = "1", login = "1", avatarUrl = "avatar_url"),
            DomainUserModel(id = "2", login = "2", avatarUrl = "avatar_url"),
            DomainUserModel(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(domainRepository.getUsersFromDatabase()).thenReturn(testData)
            Assert.assertEquals(
                "Returned data is not equals",
                testData,
                useCase.execute(testNetworkLost)
            )
        }
    }

    @Test
    fun should_return_not_equals_data_if_network_is_lost() {
        val useCase = GetUsersUseCase(domainRepository)
        val testData = listOf(
            DomainUserModel(id = "1", login = "1", avatarUrl = "avatar_url"),
            DomainUserModel(id = "2", login = "2", avatarUrl = "avatar_url"),
            DomainUserModel(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(domainRepository.getUsersFromDatabase()).thenReturn(testData)
            Assert.assertNotEquals(
                "Returned data is equals",
                testData,
                useCase.execute(testNetworkLost).first()
            )
        }
    }

    @Test
    fun verify_if_network_is_lost() {
        val useCase = GetUsersUseCase(domainRepository)
        runBlocking {
            useCase.execute(testNetworkLost)
            Mockito.verify(domainRepository, Mockito.times(1)).getUsersFromDatabase()
            Mockito.verify(domainRepository, Mockito.times(0)).getUsersFromNetwork()
            Mockito.verify(domainRepository, Mockito.times(0)).getReposFromNetwork("test")
            Mockito.verify(domainRepository, Mockito.times(0)).getReposFromDatabase("test")
        }
    }
}