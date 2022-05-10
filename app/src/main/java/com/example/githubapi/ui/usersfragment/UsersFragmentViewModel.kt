package com.example.githubapi.ui.usersfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapi.data.DomainRepositoryImpl
import com.example.githubapi.data.database.AppDatabase
import com.example.githubapi.data.database.DatabaseRepositoryImpl
import com.example.githubapi.data.network.ApiHolder
import com.example.githubapi.data.network.NetworkRepositoryImpl
import com.example.githubapi.domain.GetUsersUseCase
import com.example.githubapi.domain.models.DomainUserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class UsersFragmentViewModel : ViewModel() {

    private val retrofit = ApiHolder.retrofitService
    private val database = AppDatabase.instance
    private val networkRepository = NetworkRepositoryImpl(retrofit)
    private val databaseRepository = DatabaseRepositoryImpl(database)
    private val repositoryImpl = DomainRepositoryImpl(networkRepository, databaseRepository)
    private val getUsersUseCase = GetUsersUseCase(repositoryImpl)

    private val _userList = MutableStateFlow<List<DomainUserModel>>(emptyList())
    val userList: StateFlow<List<DomainUserModel>>
        get() = _userList

    fun getUsers(isNetworkAvailable: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            getUsersUseCase.execute(isNetworkAvailable)
                .distinctUntilChanged()
                .collectLatest {
                    _userList.value = it
                }
        }
    }
}