package com.example.githubapi.ui.reposfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapi.data.DomainRepositoryImpl
import com.example.githubapi.data.database.AppDatabase
import com.example.githubapi.data.database.DatabaseRepositoryImpl
import com.example.githubapi.data.network.ApiHolder
import com.example.githubapi.data.network.NetworkRepositoryImpl
import com.example.githubapi.domain.GetReposUseCase
import com.example.githubapi.domain.models.DomainRepoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReposFragmentViewModel : ViewModel() {

    private val retrofit = ApiHolder.retrofitService
    private val database = AppDatabase.instance
    private val networkRepository = NetworkRepositoryImpl(retrofit)
    private val databaseRepository = DatabaseRepositoryImpl(database)
    private val repositoryImpl = DomainRepositoryImpl(networkRepository, databaseRepository)
    private val getReposUseCase = GetReposUseCase(repositoryImpl)

    private var _reposList = MutableStateFlow<List<DomainRepoModel>>(emptyList())
    val reposList: StateFlow<List<DomainRepoModel>>
        get() = _reposList.asStateFlow()

    fun getRepos(isNetworkAvailable: Boolean, url: String, ownerId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _reposList.value = getReposUseCase.execute(isNetworkAvailable, url, ownerId)
        }
    }
}