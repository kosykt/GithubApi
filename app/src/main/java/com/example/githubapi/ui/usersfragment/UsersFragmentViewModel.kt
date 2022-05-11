package com.example.githubapi.ui.usersfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapi.domain.GetUsersUseCase
import com.example.githubapi.domain.models.DomainUserModel
import com.example.githubapi.utils.NetworkObserver
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

    private val _userList = MutableStateFlow<List<DomainUserModel>>(emptyList())
    val userList: StateFlow<List<DomainUserModel>>
        get() = _userList.asStateFlow()

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _userList.value = getUsersUseCase.execute(networkStatus.networkObserver())
        }
    }

    override fun onCleared() {
        super.onCleared()
        usersSubcomponentProvider.destroyUsersSubcomponent()
    }
}