package com.example.domain


import com.example.domain.models.DomainUserModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetUsersUseCaseTest {

    private val dataSourceRepository = mock<DataSourceRepository>()
    private val testNetworkAvailable = true
    private val testNetworkLost = false

    @After
    fun tearDown() {
        Mockito.reset(dataSourceRepository)
    }

    @Test
    fun should_return_not_null_data_if_network_is_available() {
        val useCase = GetUsersUseCase(dataSourceRepository)
        val testData = listOf(
            DomainUserModel(id = "1", login = "1", avatarUrl = "avatar_url"),
            DomainUserModel(id = "2", login = "2", avatarUrl = "avatar_url"),
            DomainUserModel(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(dataSourceRepository.getUsersFromNetwork())
                .thenReturn(UseCaseResponse.Success(testData))
            Assert.assertNotNull(
                "Returned data is null",
                useCase.execute(testNetworkAvailable)
            )
        }
    }

    @Test
    fun should_return_equals_data_if_network_is_available() {
        val useCase = GetUsersUseCase(dataSourceRepository)
        val testData = listOf(
            DomainUserModel(id = "1", login = "1", avatarUrl = "avatar_url"),
            DomainUserModel(id = "2", login = "2", avatarUrl = "avatar_url"),
            DomainUserModel(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(dataSourceRepository.getUsersFromNetwork())
                .thenReturn(UseCaseResponse.Success(testData))
            Assert.assertEquals(
                "Returned data is not equals",
                UseCaseResponse.Success(testData),
                useCase.execute(testNetworkAvailable)
            )
        }
    }

    @Test
    fun should_return_not_equals_data_if_network_is_available() {
        val useCase = GetUsersUseCase(dataSourceRepository)
        val testData = listOf(
            DomainUserModel(id = "1", login = "1", avatarUrl = "avatar_url"),
            DomainUserModel(id = "2", login = "2", avatarUrl = "avatar_url"),
            DomainUserModel(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(dataSourceRepository.getUsersFromNetwork()).thenReturn(UseCaseResponse.Success(testData))
            Assert.assertNotEquals(
                "Returned data is equals",
                UseCaseResponse.Error("test"),
                useCase.execute(testNetworkAvailable)
            )
        }
    }

    @Test
    fun verify_if_network_is_available() {
        val useCase = GetUsersUseCase(dataSourceRepository)
        runBlocking {
            useCase.execute(testNetworkAvailable)
            Mockito.verify(dataSourceRepository, Mockito.times(1)).getUsersFromNetwork()
            Mockito.verify(dataSourceRepository, Mockito.times(0)).getUsersFromDatabase()
            Mockito.verify(dataSourceRepository, Mockito.times(0)).getReposFromNetwork("test")
            Mockito.verify(dataSourceRepository, Mockito.times(0)).getReposFromDatabase("test")
        }
    }

    @Test
    fun should_return_not_null_data_if_network_is_lost() {
        val useCase = GetUsersUseCase(dataSourceRepository)
        val testData = listOf(
            DomainUserModel(id = "1", login = "1", avatarUrl = "avatar_url"),
            DomainUserModel(id = "2", login = "2", avatarUrl = "avatar_url"),
            DomainUserModel(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(dataSourceRepository.getUsersFromDatabase()).thenReturn(UseCaseResponse.Success(testData))
            Assert.assertNotNull(
                "Returned data is null",
                useCase.execute(testNetworkLost)
            )
        }
    }

    @Test
    fun should_return_equals_data_if_network_is_lost() {
        val useCase = GetUsersUseCase(dataSourceRepository)
        val testData = listOf(
            DomainUserModel(id = "1", login = "1", avatarUrl = "avatar_url"),
            DomainUserModel(id = "2", login = "2", avatarUrl = "avatar_url"),
            DomainUserModel(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(dataSourceRepository.getUsersFromDatabase()).thenReturn(UseCaseResponse.Success(testData))
            Assert.assertEquals(
                "Returned data is not equals",
                UseCaseResponse.Success(testData),
                useCase.execute(testNetworkLost)
            )
        }
    }

    @Test
    fun should_return_not_equals_data_if_network_is_lost() {
        val useCase = GetUsersUseCase(dataSourceRepository)
        val testData = listOf(
            DomainUserModel(id = "1", login = "1", avatarUrl = "avatar_url"),
            DomainUserModel(id = "2", login = "2", avatarUrl = "avatar_url"),
            DomainUserModel(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(dataSourceRepository.getUsersFromDatabase()).thenReturn(UseCaseResponse.Success(testData))
            Assert.assertNotEquals(
                "Returned data is equals",
                UseCaseResponse.Error("test"),
                useCase.execute(testNetworkLost)
            )
        }
    }

    @Test
    fun verify_if_network_is_lost() {
        val useCase = GetUsersUseCase(dataSourceRepository)
        runBlocking {
            useCase.execute(testNetworkLost)
            Mockito.verify(dataSourceRepository, Mockito.times(1)).getUsersFromDatabase()
            Mockito.verify(dataSourceRepository, Mockito.times(0)).getUsersFromNetwork()
            Mockito.verify(dataSourceRepository, Mockito.times(0)).getReposFromNetwork("test")
            Mockito.verify(dataSourceRepository, Mockito.times(0)).getReposFromDatabase("test")
        }
    }
}