package com.example.githubapi.ui.usersfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetUsersUseCase
import com.example.githubapi.utils.NetworkObserver
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersFragmentViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val networkStatus: NetworkObserver,
    private val usersSubcomponentProvider: UsersSubcomponentProvider
) : ViewModel() {

    private val _userList = MutableStateFlow<UsersState>(UsersState.Success(emptyList()))
    val userList: StateFlow<UsersState>
        get() = _userList.asStateFlow()

    fun getUsers() {
        _userList.value = UsersState.Loading
        viewModelScope.launch(
            Dispatchers.IO
                    + CoroutineExceptionHandler { _, throwable ->
                errorCatch(throwable)
            }
        ) {
            _userList.value =
                UsersState.Success(getUsersUseCase.execute(networkStatus.networkObserver()))
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