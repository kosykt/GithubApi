package com.example.githubapi.ui.usersfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteFavouriteUserUseCase
import com.example.domain.GetAllFavouriteUsersIdUseCase
import com.example.domain.GetUsersUseCase
import com.example.domain.SaveFavouriteUserUseCase
import com.example.domain.models.DomainUserModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersFragmentViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val usersSubcomponentProvider: UsersSubcomponentProvider,
    private val saveFavouriteUserUseCase: SaveFavouriteUserUseCase,
    private val deleteFavouriteUserUseCase: DeleteFavouriteUserUseCase,
    getAllFavouriteUsersIdUseCase: GetAllFavouriteUsersIdUseCase,
) : ViewModel() {

    private val favouriteUsersId: StateFlow<List<String>> = getAllFavouriteUsersIdUseCase.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    private val _userList = MutableStateFlow<UsersState>(UsersState.Success(emptyList()))
    val userList: StateFlow<UsersState>
        get() = _userList.asStateFlow()

    fun getUsers(networkIsAvailable: Boolean) {
        _userList.value = UsersState.Loading
        viewModelScope.launch(
            Dispatchers.IO
                    + CoroutineExceptionHandler { _, throwable ->
                errorCatch(throwable)
            }
        ) {
            _userList.value =
                UsersState.Success(getUsersUseCase.execute(networkIsAvailable))
        }
    }

    fun isAUserFavourite(userId: String): Boolean {
        return favouriteUsersId.value.contains(userId)
    }

    fun favouriteUserClickHandler(user: DomainUserModel): Boolean {
        return when (isAUserFavourite(user.id)) {
            true -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteFavouriteUserUseCase.execute(user)
                }
                false
            }
            false -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveFavouriteUserUseCase.execute(user)
                }
                true
            }
        }
    }

    private fun errorCatch(throwable: Throwable) {
        _userList.value = UsersState.Error(throwable.message.toString())
    }

    override fun onCleared() {
        super.onCleared()
        usersSubcomponentProvider.destroyUsersSubcomponent()
    }
}