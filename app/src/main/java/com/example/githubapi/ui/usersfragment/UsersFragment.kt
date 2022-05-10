package com.example.githubapi.ui.usersfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.githubapi.data.DomainRepositoryImpl
import com.example.githubapi.data.database.AppDatabase
import com.example.githubapi.data.database.DatabaseRepositoryImpl
import com.example.githubapi.data.network.ApiHolder
import com.example.githubapi.data.network.NetworkRepositoryImpl
import com.example.githubapi.databinding.FragmentUsersBinding
import com.example.githubapi.domain.GetUsersUseCase
import com.example.githubapi.domain.models.DomainUserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class UsersFragment : Fragment() {

    private val response = MutableStateFlow<List<DomainUserModel>>(emptyList())

    private val retrofit = ApiHolder.retrofitService
    private val database = AppDatabase.instance
    private val networkRepository = NetworkRepositoryImpl(retrofit)
    private val databaseRepository = DatabaseRepositoryImpl(database)
    private val repositoryImpl = DomainRepositoryImpl(networkRepository, databaseRepository)
    private val getUsersUseCase = GetUsersUseCase(repositoryImpl)

    private val adapter by lazy { UsersFragmentAdapter() }

    private var _binding: FragmentUsersBinding? = null
    private val binding: FragmentUsersBinding
        get() = _binding ?: throw RuntimeException("FragmentUsersBinding? = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.usersFragmentRecycler.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            getUsersUseCase.execute(false)
                .distinctUntilChanged()
                .collectLatest {
                    response.value = it
                }
        }

        lifecycleScope.launchWhenStarted {
            response.collect {
                refreshAdapter(it)
            }
        }
    }

    private fun refreshAdapter(models: List<DomainUserModel>) = adapter.submitList(models)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}