package com.example.githubapi.di.modules.scopes

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubapi.di.annotations.UsersScope
import com.example.githubapi.di.annotations.ViewModelKey
import com.example.githubapi.domain.DomainRepository
import com.example.githubapi.domain.GetUsersUseCase
import com.example.githubapi.ui.usersfragment.UsersFragmentViewModel
import com.example.githubapi.ui.usersfragment.UsersSubcomponentProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface UsersModule {

    @UsersScope
    @Binds
    @IntoMap
    @ViewModelKey(UsersFragmentViewModel::class)
    fun bindUsersFragmentViewModel(vm: UsersFragmentViewModel): ViewModel

    companion object {

        @UsersScope
        @Provides
        fun provideGetGetUsersUseCase(
            domainRepository: DomainRepository
        ): GetUsersUseCase {
            return GetUsersUseCase(domainRepository)
        }

        @UsersScope
        @Provides
        fun provideUsersSubcomponentProvider(
            application: Application
        ): UsersSubcomponentProvider {
            return (application as UsersSubcomponentProvider)
        }
    }
}