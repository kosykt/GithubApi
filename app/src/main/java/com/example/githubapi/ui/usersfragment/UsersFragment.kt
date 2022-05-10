package com.example.githubapi.ui.usersfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.githubapi.data.DomainRepositoryImpl
import com.example.githubapi.data.database.AppDatabase
import com.example.githubapi.data.network.ApiHolder
import com.example.githubapi.databinding.FragmentUsersBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UsersFragment : Fragment() {

    private val response = MutableStateFlow<List<DomainUserModel>>(emptyList())

    private val retrofit = ApiHolder.retrofitService
    private val database = AppDatabase.instance
    private val repositoryImpl = DomainRepositoryImpl(retrofit, database)

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
            response.value = repositoryImpl.getUserFromNetwork()
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