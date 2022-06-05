package com.example.githubapi.ui.reposfragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.domain.models.DomainRepoModel
import com.example.githubapi.databinding.FragmentReposBinding
import com.example.githubapi.ui.base.BaseFragment
import com.example.githubapi.utils.AppState
import com.example.githubapi.utils.NetworkObserver
import com.example.githubapi.utils.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ReposFragment : BaseFragment<FragmentReposBinding>() {

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

    override fun onAttach(context: Context) {
        (requireActivity().application as ReposSubcomponentProvider)
            .initReposSubcomponent()
            .inject(this)
        super.onAttach(context)
    }

    override fun getViewBinding(container: ViewGroup?) =
        FragmentReposBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reposFragmentRecycler.adapter = adapter
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
            viewModel.stateFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest {
                    renderData(it)
                }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success<*> -> {
                refreshAdapter(appState.data as List<DomainRepoModel>)
            }
            is AppState.Loading -> {
                Toast.makeText(context, "LOADING", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Toast.makeText(context, appState.error.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun refreshAdapter(models: List<DomainRepoModel>) = adapter.submitList(models)
}