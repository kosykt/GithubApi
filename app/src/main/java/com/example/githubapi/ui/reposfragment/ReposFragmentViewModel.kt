package com.example.githubapi.ui.reposfragment

import android.util.Log
import com.example.domain.*
import com.example.domain.models.DomainRepoModel
import com.example.githubapi.ui.base.BaseViewModel
import com.example.githubapi.utils.AppState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val REPOS_EXCEPTION_TAG = "REPOS_EXCEPTION_TAG"

class ReposFragmentViewModel @Inject constructor(
    private val getReposUseCase: GetReposUseCase,
    private val reposSubcomponentProvider: ReposSubcomponentProvider,
    private val saveFavouriteRepoUseCase: SaveFavouriteRepoUseCase,
    private val deleteFavouriteRepoUseCase: DeleteFavouriteRepoUseCase,
    getAllFavouriteReposIdUseCase: GetAllFavouriteReposIdUseCase
) : BaseViewModel() {

    private val favouriteReposId: StateFlow<List<String>> = getAllFavouriteReposIdUseCase.execute()
        .stateIn(
            scope = baseViewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    @Suppress("UNCHECKED_CAST")
    fun getRepos(networkIsAvailable: Boolean, login: String, ownerId: String) {
        mutableStateFlow.value = AppState.Loading
        baseViewModelScope.launch {
            try {
                val response = getReposUseCase.execute(
                    isNetworkAvailable = networkIsAvailable,
                    login = login,
                    ownerId = ownerId
                )
                when (response) {
                    is UseCaseResponse.Error -> {
                        mutableStateFlow.value = AppState.Error(response.message)
                    }
                    is UseCaseResponse.Success<*> -> {
                        mutableStateFlow.value = AppState.Success(
                            (response.data as List<DomainRepoModel>)
                        )
                    }
                }
            } catch (e: Exception) {
                mutableStateFlow.value = AppState.Error(e.message.toString())
                Log.e(REPOS_EXCEPTION_TAG, e.message.toString())
            }
        }
    }

    fun isARepoFavourite(userId: String): Boolean {
        return favouriteReposId.value.contains(userId)
    }

    fun favouriteRepoClickHandler(repoModel: DomainRepoModel): Boolean {
        return when (isARepoFavourite(repoModel.id)) {
            true -> {
                baseViewModelScope.launch {
                    try {
                        deleteFavouriteRepoUseCase.execute(repoModel)
                    } catch (e: Exception) {
                        Log.e(REPOS_EXCEPTION_TAG, e.message.toString())
                    }
                }
                false
            }
            false -> {
                baseViewModelScope.launch {
                    try {
                        saveFavouriteRepoUseCase.execute(repoModel)
                    } catch (e: Exception) {
                        Log.e(REPOS_EXCEPTION_TAG, e.message.toString())
                    }
                }
                true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        reposSubcomponentProvider.destroyReposSubcomponent()
    }
}