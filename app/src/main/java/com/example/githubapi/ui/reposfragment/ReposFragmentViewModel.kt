package com.example.githubapi.ui.reposfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteFavouriteRepoUseCase
import com.example.domain.GetAllFavouriteReposIdUseCase
import com.example.domain.GetReposUseCase
import com.example.domain.SaveFavouriteRepoUseCase
import com.example.domain.models.DomainRepoModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReposFragmentViewModel @Inject constructor(
    private val getReposUseCase: GetReposUseCase,
    private val reposSubcomponentProvider: ReposSubcomponentProvider,
    private val saveFavouriteRepoUseCase: SaveFavouriteRepoUseCase,
    private val deleteFavouriteRepoUseCase: DeleteFavouriteRepoUseCase,
    getAllFavouriteReposIdUseCase: GetAllFavouriteReposIdUseCase
) : ViewModel() {

    private val favouriteReposId: StateFlow<List<String>> = getAllFavouriteReposIdUseCase.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    private var _reposList = MutableStateFlow<ReposState>(ReposState.Success(emptyList()))
    val reposList: StateFlow<ReposState>
        get() = _reposList.asStateFlow()

    fun getRepos(networkIsAvailable: Boolean, url: String, ownerId: String) {
        _reposList.value = ReposState.Loading
        viewModelScope.launch(
            Dispatchers.IO
                    + CoroutineExceptionHandler { _, throwable ->
                errorCatch(throwable)
            }
        ) {
            _reposList.value = ReposState.Success(
                getReposUseCase.execute(
                    isNetworkAvailable = networkIsAvailable,
                    url = url,
                    ownerId = ownerId
                )
            )
        }
    }

    fun isARepoFavourite(userId: String): Boolean {
        return favouriteReposId.value.contains(userId)
    }

    fun favouriteRepoClickHandler(repoModel: DomainRepoModel): Boolean {
        return when (isARepoFavourite(repoModel.id)) {
            true -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteFavouriteRepoUseCase.execute(repoModel)
                }
                false
            }
            false -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveFavouriteRepoUseCase.execute(repoModel)
                }
                true
            }
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