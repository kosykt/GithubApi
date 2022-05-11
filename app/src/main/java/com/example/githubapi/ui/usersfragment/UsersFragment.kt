package com.example.githubapi.ui.usersfragment

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
import androidx.navigation.fragment.findNavController
import com.example.githubapi.databinding.FragmentUsersBinding
import com.example.domain.models.DomainUserModel
import com.example.githubapi.utils.ViewModelFactory
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class UsersFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: UsersFragmentViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UsersFragmentViewModel::class.java]
    }
    private val adapter by lazy {
        UsersFragmentAdapter(this::navigateToReposFragment)
    }
    private var _binding: FragmentUsersBinding? = null
    private val binding: FragmentUsersBinding
        get() = _binding ?: throw RuntimeException("FragmentUsersBinding? = null")

    override fun onAttach(context: Context) {
        (requireActivity().application as UsersSubcomponentProvider)
            .initUsersSubcomponent()
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
        viewModel.getUsers()
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.userList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest {
                    renderData(it)
                }
        }
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.coroutineContext.cancelChildren()
    }

    private fun renderData(usersState: UsersState) {
        when (usersState) {
            is UsersState.Success -> {
                refreshAdapter(usersState.response)
            }
            is UsersState.Loading -> {
                Toast.makeText(context, "LOADING", Toast.LENGTH_SHORT).show()
            }
            is UsersState.Error -> {
                Toast.makeText(context, usersState.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun refreshAdapter(models: List<DomainUserModel>) = adapter.submitList(models)

    private fun navigateToReposFragment(model: DomainUserModel) {
        findNavController().navigate(
            UsersFragmentDirections.actionUsersFragmentToReposFragment(
                arrayOf(model.repos_url, model.id)
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}