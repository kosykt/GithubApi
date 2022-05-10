package com.example.githubapi.ui.usersfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.githubapi.databinding.FragmentUsersBinding
import com.example.githubapi.domain.models.DomainUserModel
import com.example.githubapi.utils.networkObserver
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

class UsersFragment : Fragment() {

    private val viewModel by viewModels<UsersFragmentViewModel>()

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

        lifecycleScope.launchWhenCreated {
            networkObserver(requireContext())
                .distinctUntilChanged()
                .collect {
                    viewModel.getUsers(it)
                }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.userList
                .collectLatest {
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