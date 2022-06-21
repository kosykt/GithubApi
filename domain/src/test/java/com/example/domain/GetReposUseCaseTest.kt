package com.example.domain

import com.example.domain.models.DomainRepoModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetReposUseCaseTest {

    private val dataSourceRepository = mock<DataSourceRepository>()
    private val testNetworkAvailable = true
    private val testNetworkLost = false
    private val testUrl = "url"
    private val testOwnerId = "1"

    @After
    fun tearDown() {
        Mockito.reset(dataSourceRepository)
    }

    @Test
    fun should_return_not_null_data_if_network_is_available() {
        val useCase = GetReposUseCase(dataSourceRepository)
        val testData = listOf(
            DomainRepoModel(id = "1", ownerId = "1", name = "name"),
            DomainRepoModel(id = "2", ownerId = "2", name = "name"),
            DomainRepoModel(id = "3", ownerId = "3", name = "name"),
        )
        runBlocking {
            Mockito.`when`(dataSourceRepository.getReposFromNetwork(testUrl)).thenReturn(testData)
            Assert.assertNotNull(
                "Returned data is null",
                useCase.execute(testNetworkAvailable, testUrl, testOwnerId)
            )
        }
    }

    @Test
    fun should_get_equals_data_if_network_is_available() {
        val useCase = GetReposUseCase(dataSourceRepository)
        val testData = listOf(
            DomainRepoModel(id = "1", ownerId = "1", name = "name"),
            DomainRepoModel(id = "2", ownerId = "2", name = "name"),
            DomainRepoModel(id = "3", ownerId = "3", name = "name"),
        )
        runBlocking {
            Mockito.`when`(dataSourceRepository.getReposFromNetwork(testUrl)).thenReturn(testData)
            Assert.assertEquals(
                "Returned data is not equals",
                testData,
                useCase.execute(testNetworkAvailable, testUrl, testOwnerId)
            )
        }
    }

    @Test
    fun should_get_not_equals_data_if_network_is_available() {
        val useCase = GetReposUseCase(dataSourceRepository)
        val testData = listOf(
            DomainRepoModel(id = "1", ownerId = "1", name = "name"),
            DomainRepoModel(id = "2", ownerId = "2", name = "name"),
            DomainRepoModel(id = "3", ownerId = "3", name = "name"),
        )
        runBlocking {
            Mockito.`when`(dataSourceRepository.getReposFromNetwork(testUrl)).thenReturn(testData)
            Assert.assertNotEquals(
                "Returned data is equals",
                testData,
                useCase.execute(testNetworkAvailable, testUrl, testOwnerId).first()
            )
        }
    }

    @Test
    fun verify_if_network_is_available() {
        val useCase = GetReposUseCase(dataSourceRepository)
        runBlocking {
            useCase.execute(testNetworkAvailable, testUrl, testOwnerId)
            Mockito.verify(dataSourceRepository, Mockito.times(1))
                .getReposFromNetwork(testUrl)
            Mockito.verify(dataSourceRepository, Mockito.times(0))
                .getReposFromNetwork(testOwnerId)
            Mockito.verify(dataSourceRepository, Mockito.times(0))
                .getUsersFromNetwork()
            Mockito.verify(dataSourceRepository, Mockito.times(0))
                .getUsersFromDatabase()
        }
    }

    @Test
    fun should_return_not_null_data_if_network_is_lost() {
        val useCase = GetReposUseCase(dataSourceRepository)
        val testData = listOf(
            DomainRepoModel(id = "1", ownerId = "1", name = "name"),
            DomainRepoModel(id = "2", ownerId = "2", name = "name"),
            DomainRepoModel(id = "3", ownerId = "3", name = "name"),
        )
        runBlocking {
            Mockito.`when`(dataSourceRepository.getReposFromDatabase(testOwnerId)).thenReturn(testData)
            Assert.assertNotNull(
                "Returned data is null",
                useCase.execute(testNetworkLost, testUrl, testOwnerId)
            )
        }
    }

    @Test
    fun should_get_equals_data_if_network_is_lost() {
        val useCase = GetReposUseCase(dataSourceRepository)
        val testData = listOf(
            DomainRepoModel(id = "1", ownerId = "1", name = "name"),
            DomainRepoModel(id = "2", ownerId = "2", name = "name"),
            DomainRepoModel(id = "3", ownerId = "3", name = "name"),
        )
        runBlocking {
            Mockito.`when`(dataSourceRepository.getReposFromDatabase(testOwnerId)).thenReturn(testData)
            Assert.assertEquals(
                "Returned data is not equals",
                testData,
                useCase.execute(testNetworkLost, testUrl, testOwnerId)
            )
        }
    }

    @Test
    fun should_get_not_equals_data_if_network_is_lost() {
        val useCase = GetReposUseCase(dataSourceRepository)
        val testData = listOf(
            DomainRepoModel(id = "1", ownerId = "1", name = "name"),
            DomainRepoModel(id = "2", ownerId = "2", name = "name"),
            DomainRepoModel(id = "3", ownerId = "3", name = "name"),
        )
        runBlocking {
            Mockito.`when`(dataSourceRepository.getReposFromDatabase(testOwnerId)).thenReturn(testData)
            Assert.assertNotEquals(
                "Returned data is equals",
                testData,
                useCase.execute(testNetworkLost, testUrl, testOwnerId).first()
            )
        }
    }

    @Test
    fun verify_if_network_is_lost() {
        val useCase = GetReposUseCase(dataSourceRepository)
        runBlocking {
            useCase.execute(testNetworkLost, testUrl, testOwnerId)
            Mockito.verify(dataSourceRepository, Mockito.times(0))
                .getReposFromNetwork(testUrl)
            Mockito.verify(dataSourceRepository, Mockito.times(1))
                .getReposFromDatabase(testOwnerId)
            Mockito.verify(dataSourceRepository, Mockito.times(0))
                .getUsersFromNetwork()
            Mockito.verify(dataSourceRepository, Mockito.times(0))
                .getUsersFromDatabase()
        }
    }
}