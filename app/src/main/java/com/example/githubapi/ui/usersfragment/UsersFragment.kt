package com.example.githubapi.ui.usersfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.githubapi.databinding.FragmentUsersBinding
import com.example.domain.models.DomainUserModel
import com.example.githubapi.utils.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
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

        lifecycleScope.launchWhenStarted {
            viewModel.userList
                .collectLatest {
                    refreshAdapter(it)
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