package com.example.githubapi.ui.reposfragment

import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteFavouriteRepoUseCase
import com.example.domain.GetAllFavouriteReposIdUseCase
import com.example.domain.GetReposUseCase
import com.example.domain.SaveFavouriteRepoUseCase
import com.example.domain.models.DomainRepoModel
import com.example.githubapi.ui.base.BaseViewModel
import com.example.githubapi.utils.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class ReposFragmentViewModel @Inject constructor(
    private val getReposUseCase: GetReposUseCase,
    private val reposSubcomponentProvider: ReposSubcomponentProvider,
    private val saveFavouriteRepoUseCase: SaveFavouriteRepoUseCase,
    private val deleteFavouriteRepoUseCase: DeleteFavouriteRepoUseCase,
    getAllFavouriteReposIdUseCase: GetAllFavouriteReposIdUseCase
) : BaseViewModel() {

    private val favouriteReposId: StateFlow<List<String>> = getAllFavouriteReposIdUseCase.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    fun getRepos(networkIsAvailable: Boolean, login: String, ownerId: String) {
        mutableStateFlow.value = AppState.Loading
        tryLaunch {
            mutableStateFlow.value = AppState.Success(
                getReposUseCase.execute(
                    isNetworkAvailable = networkIsAvailable,
                    login = login,
                    ownerId = ownerId
                )
            )
        }.start(Dispatchers.IO)
    }

    fun isARepoFavourite(userId: String): Boolean {
        return favouriteReposId.value.contains(userId)
    }

    fun favouriteRepoClickHandler(repoModel: DomainRepoModel): Boolean {
        return when (isARepoFavourite(repoModel.id)) {
            true -> {
                tryLaunch {
                    deleteFavouriteRepoUseCase.execute(repoModel)
                }.start(Dispatchers.IO)
                false
            }
            false -> {
                tryLaunch {
                    saveFavouriteRepoUseCase.execute(repoModel)
                }.start(Dispatchers.IO)
                true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        reposSubcomponentProvider.destroyReposSubcomponent()
    }
}