package com.example.githubapi.ui.reposfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.domain.models.DomainRepoModel
import com.example.githubapi.databinding.FragmentUsersBinding
import com.example.githubapi.utils.NetworkObserver
import com.example.githubapi.utils.ViewModelFactory
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ReposFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var networkObserver: NetworkObserver
    private val viewModel: ReposFragmentViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ReposFragmentViewModel::class.java]
    }
    private val args by navArgs<ReposFragmentArgs>()
    private val adapter by lazy {
        ReposFragmentAdapter(
            isFavourite = viewModel::isARepoFavourite,
            favouriteClickHandler = viewModel::favouriteRepoClickHandler
        )
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
        lifecycleScope.launchWhenCreated {
            networkObserver.networkIsAvailable()
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .distinctUntilChanged()
                .collectLatest {
                    viewModel.getRepos(
                        networkIsAvailable = it,
                        login = args.userInfo.first(),
                        ownerId = args.userInfo.last()
                    )
                }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.reposList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest {
                    renderData(it)
                }
        }
    }

    private fun renderData(reposState: ReposState) {
        when (reposState) {
            is ReposState.Success -> {
                refreshAdapter(reposState.response)
            }
            is ReposState.Loading -> {
                Toast.makeText(context, "LOADING", Toast.LENGTH_SHORT).show()
            }
            is ReposState.Error -> {
                Toast.makeText(context, reposState.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.coroutineContext.cancelChildren()
    }

    private fun refreshAdapter(models: List<DomainRepoModel>) = adapter.submitList(models)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}