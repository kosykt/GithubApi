package com.example.githubapi.ui.reposfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetReposUseCase
import com.example.domain.models.DomainRepoModel
import com.example.githubapi.utils.NetworkObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReposFragmentViewModel @Inject constructor(
    private val getReposUseCase: GetReposUseCase,
    private val networkStatus: NetworkObserver,
    private val reposSubcomponentProvider: ReposSubcomponentProvider,
) : ViewModel() {

    private var _reposList = MutableStateFlow<List<DomainRepoModel>>(emptyList())
    val reposList: StateFlow<List<DomainRepoModel>>
        get() = _reposList.asStateFlow()

    fun getRepos(url: String, ownerId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _reposList.value = getReposUseCase.execute(
                isNetworkAvailable = networkStatus.networkObserver(),
                url = url,
                ownerId = ownerId
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        reposSubcomponentProvider.destroyReposSubcomponent()
    }
}