package com.example.githubapi.ui.reposfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetReposUseCase
import com.example.githubapi.utils.NetworkObserver
import kotlinx.coroutines.CoroutineExceptionHandler
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

    private var _reposList = MutableStateFlow<ReposState>(ReposState.Success(emptyList()))
    val reposList: StateFlow<ReposState>
        get() = _reposList.asStateFlow()

    fun getRepos(url: String, ownerId: String) {
        _reposList.value = ReposState.Loading
        viewModelScope.launch(
            Dispatchers.IO
                    + CoroutineExceptionHandler { _, throwable ->
                errorCatch(throwable)
            }
        ) {
            _reposList.value = ReposState.Success(
                getReposUseCase.execute(
                    isNetworkAvailable = networkStatus.networkObserver(),
                    url = url,
                    ownerId = ownerId
                )
            )
        }
    }

    private fun errorCatch(throwable: Throwable) {
        _reposList.value = ReposState.Error(throwable.message.toString())
    }

    override fun onCleared() {
        super.onCleared()
        reposSubcomponentProvider.destroyReposSubcomponent()
    }
}