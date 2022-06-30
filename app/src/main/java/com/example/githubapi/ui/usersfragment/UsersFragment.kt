package com.example.githubapi.ui.usersfragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.models.DomainUserModel
import com.example.githubapi.databinding.FragmentUsersBinding
import com.example.githubapi.ui.base.BaseFragment
import com.example.githubapi.utils.AppState
import com.example.githubapi.utils.NetworkObserver
import com.example.githubapi.utils.ViewModelFactory
import com.example.githubapi.utils.imageloader.AppImageLoader
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class UsersFragment : BaseFragment<FragmentUsersBinding>() {

    @Inject
    lateinit var appImageLoader: AppImageLoader

    @Inject
    lateinit var networkObserver: NetworkObserver

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: UsersFragmentViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UsersFragmentViewModel::class.java]
    }
    private val adapter by lazy {
        UsersFragmentAdapter(
            navigate = this::navigateToReposFragment,
            isFavourite = viewModel::isAUserFavourite,
            favouriteClickHandler = viewModel::favouriteUserClickHandler,
            appImageLoader = appImageLoader
        )
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as UsersSubcomponentProvider)
            .initUsersSubcomponent()
            .inject(this)
        super.onAttach(context)
    }

    override fun getViewBinding(container: ViewGroup?) =
        FragmentUsersBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.usersFragmentRecycler.adapter = adapter
        lifecycleScope.launchWhenCreated {
            networkObserver.networkIsAvailable()
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .distinctUntilChanged()
                .collectLatest {
                    viewModel.getUsers(it)
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
                refreshAdapter(appState.data as List<DomainUserModel>)
                binding.usersProgressBar.visibility = View.GONE
            }
            is AppState.Loading -> {
                binding.usersProgressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                Toast.makeText(context, appState.error, Toast.LENGTH_SHORT).show()
                binding.usersProgressBar.visibility = View.GONE
            }
        }
    }

    private fun refreshAdapter(models: List<DomainUserModel>) = adapter.submitList(models)

    private fun navigateToReposFragment(model: DomainUserModel) {
        findNavController().navigate(
            UsersFragmentDirections.actionUsersFragmentToReposFragment(
                arrayOf(model.login, model.id)
            )
        )
    }
}