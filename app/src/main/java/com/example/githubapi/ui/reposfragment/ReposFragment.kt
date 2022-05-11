package com.example.githubapi.ui.reposfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.githubapi.databinding.FragmentUsersBinding
import com.example.githubapi.domain.models.DomainRepoModel
import com.example.githubapi.utils.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ReposFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ReposFragmentViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ReposFragmentViewModel::class.java]
    }
    private val args by navArgs<ReposFragmentArgs>()
    private val adapter by lazy {
        ReposFragmentAdapter()
    }
    private var _binding: FragmentUsersBinding? = null
    private val binding: FragmentUsersBinding
        get() = _binding ?: throw  RuntimeException("FragmentUsersBinding? = null")

    override fun onAttach(context: Context) {
        (requireActivity().application as ReposSubcomponentProvider)
            .initReposSubcomponent()
            .inject(this)
        super.onAttach(context)
    }

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

        viewModel.getRepos(args.userInfo.first(), args.userInfo.last())

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