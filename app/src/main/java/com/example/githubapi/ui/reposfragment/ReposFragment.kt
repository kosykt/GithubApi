package com.example.githubapi.ui.reposfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.githubapi.databinding.FragmentUsersBinding
import com.example.githubapi.domain.models.DomainRepoModel
import com.example.githubapi.utils.NetworkStatus
import kotlinx.coroutines.flow.collectLatest

class ReposFragment : Fragment() {

    private val args by navArgs<ReposFragmentArgs>()
    private val viewModel by viewModels<ReposFragmentViewModel>()
    private val adapter by lazy { ReposFragmentAdapter() }
    private val networkStatus by lazy { NetworkStatus(requireContext()) }

    private var _binding: FragmentUsersBinding? = null
    private val binding: FragmentUsersBinding
        get() = _binding ?: throw  RuntimeException("FragmentUsersBinding? = null")

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

        networkStatus.networkObserver().observe(viewLifecycleOwner) {
            viewModel.getRepos(
                isNetworkAvailable = it,
                url = args.userInfo.last(),
                ownerId = args.userInfo.first()
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.reposList
                .collectLatest {
                    refreshAdapter(it)
                }
        }
    }

    private fun refreshAdapter(models: List<DomainRepoModel>) = adapter.submitList(models)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}