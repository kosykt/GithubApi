package com.example.data.repository

import com.example.data.database.model.HistoryCacheRepoEntity
import com.example.data.database.model.HistoryCacheUserEntity
import com.example.data.network.model.RepoDTO
import com.example.data.network.model.RepoOwner
import com.example.data.network.model.UserDTO
import com.example.data.toListDomainRepoModel
import com.example.data.toListDomainUserModel
import com.example.data.toListRepoEntity
import com.example.data.toListUserEntity
import com.example.domain.UseCaseResponse
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import retrofit2.Response

class DataSourceRepositoryImplTest {

    private val network = mock<NetworkRepository>()
    private val database = mock<DatabaseRepository>()
    private val testUrl = "url"
    private val testOwnerId = "1"

    @After
    fun tearDown() {
        Mockito.reset(network)
        Mockito.reset(database)
    }

    @Test
    fun should_return_not_null_users_data_if_network_is_available(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            UserDTO(id = "1", login = "1", avatarUrl = "avatar_url"),
            UserDTO(id = "2", login = "2", avatarUrl = "avatar_url"),
            UserDTO(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(network.getUsers()).thenReturn(Response.success(testData))
            Assert.assertNotNull(
                "Returned data is null",
                domainRepositoryImpl.getUsersFromNetwork()
            )
        }
    }

    @Test
    fun should_return_equals_users_data_if_network_is_available(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            UserDTO(id = "1", login = "1", avatarUrl = "avatar_url"),
            UserDTO(id = "2", login = "2", avatarUrl = "avatar_url"),
            UserDTO(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(network.getUsers()).thenReturn(Response.success(testData))
            Assert.assertEquals(
                "Returned data is not equals",
                UseCaseResponse.Success(testData.toListDomainUserModel()),
                domainRepositoryImpl.getUsersFromNetwork()
            )
        }
    }

    @Test
    fun should_return_not_equals_users_data_if_network_is_available(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            UserDTO(id = "1", login = "1", avatarUrl = "avatar_url"),
            UserDTO(id = "2", login = "2", avatarUrl = "avatar_url"),
            UserDTO(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(network.getUsers()).thenReturn(Response.success(testData))
            Assert.assertNotEquals(
                "Returned data is equals",
                testData,
                domainRepositoryImpl.getUsersFromNetwork()
            )
        }
    }

    @Test
    fun verify_users_if_network_is_available(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            UserDTO(id = "1", login = "1", avatarUrl = "avatar_url"),
            UserDTO(id = "2", login = "2", avatarUrl = "avatar_url"),
            UserDTO(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(network.getUsers()).thenReturn(Response.success(testData))
            domainRepositoryImpl.getUsersFromNetwork()
            Mockito.verify(database, Mockito.times(1)).insertUsers(testData.toListUserEntity())
            Mockito.verify(database, Mockito.times(0)).insertRepos(emptyList())
            Mockito.verify(network, Mockito.times(1)).getUsers()
            Mockito.verify(database, Mockito.times(0)).getUsers()
            Mockito.verify(network, Mockito.times(0)).getRepos(testUrl)
            Mockito.verify(database, Mockito.times(0)).getRepos(testOwnerId)
        }
    }

    @Test
    fun should_return_not_null_users_data_if_network_is_lost(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            HistoryCacheUserEntity(id = "1", login = "1", avatarUrl = "avatar_url"),
            HistoryCacheUserEntity(id = "2", login = "2", avatarUrl = "avatar_url"),
            HistoryCacheUserEntity(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(database.getUsers()).thenReturn(testData)
            Assert.assertNotNull(
                "Returned data is null",
                domainRepositoryImpl.getUsersFromDatabase()
            )
        }
    }

    @Test
    fun should_return_equals_users_data_if_network_is_lost(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            HistoryCacheUserEntity(id = "1", login = "1", avatarUrl = "avatar_url"),
            HistoryCacheUserEntity(id = "2", login = "2", avatarUrl = "avatar_url"),
            HistoryCacheUserEntity(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(database.getUsers()).thenReturn(testData)
            Assert.assertEquals(
                "Returned data is not equals",
                UseCaseResponse.Success(testData.toListDomainUserModel()),
                domainRepositoryImpl.getUsersFromDatabase()
            )
        }
    }

    @Test
    fun should_return_not_equals_users_data_if_network_is_lost(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            HistoryCacheUserEntity(id = "1", login = "1", avatarUrl = "avatar_url"),
            HistoryCacheUserEntity(id = "2", login = "2", avatarUrl = "avatar_url"),
            HistoryCacheUserEntity(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(database.getUsers()).thenReturn(testData)
            Assert.assertNotEquals(
                "Returned data is equals",
                testData,
                domainRepositoryImpl.getUsersFromDatabase()
            )
        }
    }

    @Test
    fun verify_users_if_network_is_lost(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            HistoryCacheUserEntity(id = "1", login = "1", avatarUrl = "avatar_url"),
            HistoryCacheUserEntity(id = "2", login = "2", avatarUrl = "avatar_url"),
            HistoryCacheUserEntity(id = "3", login = "3", avatarUrl = "avatar_url"),
        )
        runBlocking {
            Mockito.`when`(database.getUsers()).thenReturn(testData)
            domainRepositoryImpl.getUsersFromDatabase()
            Mockito.verify(database, Mockito.times(0)).insertUsers(emptyList())
            Mockito.verify(database, Mockito.times(0)).insertRepos(emptyList())
            Mockito.verify(network, Mockito.times(0)).getUsers()
            Mockito.verify(database, Mockito.times(1)).getUsers()
            Mockito.verify(network, Mockito.times(0)).getRepos(testUrl)
            Mockito.verify(database, Mockito.times(0)).getRepos(testOwnerId)
        }
    }

    @Test
    fun should_return_not_null_repos_data_if_network_is_available(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            RepoDTO(id = "1", owner = RepoOwner(id = "1"), name = "name"),
            RepoDTO(id = "2", owner = RepoOwner(id = "2"), name = "name"),
            RepoDTO(id = "3", owner = RepoOwner(id = "3"), name = "name"),
        )
        runBlocking {
            Mockito.`when`(network.getRepos(testUrl)).thenReturn(Response.success(testData))
            Assert.assertNotNull(
                "Returned data is null",
                domainRepositoryImpl.getReposFromNetwork(testUrl)
            )
        }
    }

    @Test
    fun should_return_equals_repos_data_if_network_is_available(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            RepoDTO(id = "1", owner = RepoOwner(id = "1"), name = "name"),
            RepoDTO(id = "2", owner = RepoOwner(id = "2"), name = "name"),
            RepoDTO(id = "3", owner = RepoOwner(id = "3"), name = "name"),
        )
        runBlocking {
            Mockito.`when`(network.getRepos(testUrl)).thenReturn(Response.success(testData))
            Assert.assertEquals(
                "Returned data is not equals",
                UseCaseResponse.Success(testData.toListDomainRepoModel()),
                domainRepositoryImpl.getReposFromNetwork(testUrl)
            )
        }
    }

    @Test
    fun should_return_not_equals_repos_data_if_network_is_available(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            RepoDTO(id = "1", owner = RepoOwner(id = "1"), name = "name"),
            RepoDTO(id = "2", owner = RepoOwner(id = "2"), name = "name"),
            RepoDTO(id = "3", owner = RepoOwner(id = "3"), name = "name"),
        )
        runBlocking {
            Mockito.`when`(network.getRepos(testUrl)).thenReturn(Response.success(testData))
            Assert.assertNotEquals(
                "Returned data is equals",
                testData,
                domainRepositoryImpl.getReposFromNetwork(testUrl)
            )
        }
    }

    @Test
    fun verify_repos_if_network_is_available(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            RepoDTO(id = "1", owner = RepoOwner(id = "1"), name = "name"),
            RepoDTO(id = "2", owner = RepoOwner(id = "2"), name = "name"),
            RepoDTO(id = "3", owner = RepoOwner(id = "3"), name = "name"),
        )
        runBlocking {
            Mockito.`when`(network.getRepos(testUrl)).thenReturn(Response.success(testData))
            domainRepositoryImpl.getReposFromNetwork(testUrl)
            Mockito.verify(database, Mockito.times(0)).insertUsers(emptyList())
            Mockito.verify(database, Mockito.times(1)).insertRepos(testData.toListRepoEntity())
            Mockito.verify(network, Mockito.times(0)).getUsers()
            Mockito.verify(database, Mockito.times(0)).getUsers()
            Mockito.verify(network, Mockito.times(1)).getRepos(testUrl)
            Mockito.verify(database, Mockito.times(0)).getRepos(testOwnerId)
        }
    }

    @Test
    fun should_return_not_null_repos_data_if_network_is_lost(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            HistoryCacheRepoEntity(id = "1", ownerId = "1", name = "name"),
            HistoryCacheRepoEntity(id = "2", ownerId = "2", name = "name"),
            HistoryCacheRepoEntity(id = "3", ownerId = "3", name = "name"),
        )
        runBlocking {
            Mockito.`when`(database.getRepos(testOwnerId)).thenReturn(testData)
            Assert.assertNotNull(
                "Returned data is null",
                domainRepositoryImpl.getReposFromDatabase(testOwnerId)
            )
        }
    }

    @Test
    fun should_return_equals_repos_data_if_network_is_lost(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            HistoryCacheRepoEntity(id = "1", ownerId = "1", name = "name"),
            HistoryCacheRepoEntity(id = "2", ownerId = "2", name = "name"),
            HistoryCacheRepoEntity(id = "3", ownerId = "3", name = "name"),
        )
        runBlocking {
            Mockito.`when`(database.getRepos(testOwnerId)).thenReturn(testData)
            Assert.assertEquals(
                "Returned data is not equals",
                UseCaseResponse.Success(testData.toListDomainRepoModel()),
                domainRepositoryImpl.getReposFromDatabase(testOwnerId)
            )
        }
    }

    @Test
    fun should_return_not_equals_repos_data_if_network_is_lost(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            HistoryCacheRepoEntity(id = "1", ownerId = "1", name = "name"),
            HistoryCacheRepoEntity(id = "2", ownerId = "2", name = "name"),
            HistoryCacheRepoEntity(id = "3", ownerId = "3", name = "name"),
        )
        runBlocking {
            Mockito.`when`(database.getRepos(testOwnerId)).thenReturn(testData)
            Assert.assertNotEquals(
                "Returned data is equals",
                testData,
                domainRepositoryImpl.getReposFromDatabase(testOwnerId)
            )
        }
    }

    @Test
    fun verify_repos_if_network_is_lost(){
        val domainRepositoryImpl = DataSourceRepositoryImpl(network, database)
        val testData = listOf(
            HistoryCacheRepoEntity(id = "1", ownerId = "1", name = "name"),
            HistoryCacheRepoEntity(id = "2", ownerId = "2", name = "name"),
            HistoryCacheRepoEntity(id = "3", ownerId = "3", name = "name"),
        )
        runBlocking {
            Mockito.`when`(database.getRepos(testOwnerId)).thenReturn(testData)
            domainRepositoryImpl.getReposFromDatabase(testOwnerId)
            Mockito.verify(database, Mockito.times(0)).insertUsers(emptyList())
            Mockito.verify(database, Mockito.times(0)).insertRepos(emptyList())
            Mockito.verify(network, Mockito.times(0)).getUsers()
            Mockito.verify(database, Mockito.times(0)).getUsers()
            Mockito.verify(network, Mockito.times(0)).getRepos(testUrl)
            Mockito.verify(database, Mockito.times(1)).getRepos(testOwnerId)
        }
    }
}