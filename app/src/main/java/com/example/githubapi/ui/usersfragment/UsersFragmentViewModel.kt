package com.example.githubapi.ui.usersfragment

import android.util.Log
import com.example.domain.*
import com.example.domain.models.DomainUserModel
import com.example.githubapi.ui.base.BaseViewModel
import com.example.githubapi.utils.AppState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val USER_EXCEPTION_TAG = "USER_EXCEPTION_TAG"

class UsersFragmentViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val usersSubcomponentProvider: UsersSubcomponentProvider,
    private val saveFavouriteUserUseCase: SaveFavouriteUserUseCase,
    private val deleteFavouriteUserUseCase: DeleteFavouriteUserUseCase,
    getAllFavouriteUsersIdUseCase: GetAllFavouriteUsersIdUseCase,
) : BaseViewModel() {

    private val favouriteUsersId: StateFlow<List<String>> = getAllFavouriteUsersIdUseCase.execute()
        .stateIn(
            scope = baseViewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    @Suppress("UNCHECKED_CAST")
    fun getUsers(networkIsAvailable: Boolean) {
        mutableStateFlow.value = AppState.Loading
        baseViewModelScope.launch {
            try {
                val response = getUsersUseCase.execute(networkIsAvailable)
                when (response) {
                    is UseCaseResponse.Error -> {
                        mutableStateFlow.value = AppState.Error(response.message)
                    }
                    is UseCaseResponse.Success<*> -> {
                        mutableStateFlow.value = AppState.Success(
                            (response.data as List<DomainUserModel>)
                        )
                    }
                }
            } catch (e: Exception) {
                mutableStateFlow.value = AppState.Error(e.message.toString())
                Log.e(USER_EXCEPTION_TAG, e.message.toString())
            }
        }
    }

    fun isAUserFavourite(userId: String): Boolean {
        return favouriteUsersId.value.contains(userId)
    }

    fun favouriteUserClickHandler(user: DomainUserModel): Boolean {
        return when (isAUserFavourite(user.id)) {
            true -> {
                baseViewModelScope.launch {
                    try {
                        deleteFavouriteUserUseCase.execute(user)
                    } catch (e: Exception) {
                        Log.e(USER_EXCEPTION_TAG, e.message.toString())
                    }
                }
                false
            }
            false -> {
                baseViewModelScope.launch {
                    try {
                        saveFavouriteUserUseCase.execute(user)
                    } catch (e: Exception) {
                        Log.e(USER_EXCEPTION_TAG, e.message.toString())
                    }
                }
                true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        usersSubcomponentProvider.destroyUsersSubcomponent()
    }
}