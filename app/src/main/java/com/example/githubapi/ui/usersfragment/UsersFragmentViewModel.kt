package com.example.githubapi.ui.usersfragment

import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteFavouriteUserUseCase
import com.example.domain.GetAllFavouriteUsersIdUseCase
import com.example.domain.GetUsersUseCase
import com.example.domain.SaveFavouriteUserUseCase
import com.example.domain.models.DomainUserModel
import com.example.githubapi.ui.base.BaseViewModel
import com.example.githubapi.utils.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class UsersFragmentViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val usersSubcomponentProvider: UsersSubcomponentProvider,
    private val saveFavouriteUserUseCase: SaveFavouriteUserUseCase,
    private val deleteFavouriteUserUseCase: DeleteFavouriteUserUseCase,
    getAllFavouriteUsersIdUseCase: GetAllFavouriteUsersIdUseCase,
) : BaseViewModel() {

    private val favouriteUsersId: StateFlow<List<String>> = getAllFavouriteUsersIdUseCase.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    fun getUsers(networkIsAvailable: Boolean) {
        mutableStateFlow.value = AppState.Loading
        tryLaunch {
            mutableStateFlow.value = AppState.Success(getUsersUseCase.execute(networkIsAvailable))
        }.start(Dispatchers.IO)
    }

    fun isAUserFavourite(userId: String): Boolean {
        return favouriteUsersId.value.contains(userId)
    }

    fun favouriteUserClickHandler(user: DomainUserModel): Boolean {
        return when (isAUserFavourite(user.id)) {
            true -> {
                tryLaunch {
                    deleteFavouriteUserUseCase.execute(user)
                }.start(Dispatchers.IO)
                false
            }
            false -> {
                tryLaunch {
                    saveFavouriteUserUseCase.execute(user)
                }.start(Dispatchers.IO)
                true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        usersSubcomponentProvider.destroyUsersSubcomponent()
    }
}