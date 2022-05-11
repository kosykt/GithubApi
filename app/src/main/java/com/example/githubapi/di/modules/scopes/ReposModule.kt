package com.example.githubapi.di.modules.scopes

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubapi.di.annotations.ReposScope
import com.example.githubapi.di.annotations.ViewModelKey
import com.example.githubapi.domain.DomainRepository
import com.example.githubapi.domain.GetReposUseCase
import com.example.githubapi.ui.reposfragment.ReposFragmentViewModel
import com.example.githubapi.ui.reposfragment.ReposSubcomponentProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ReposModule {

    @ReposScope
    @Binds
    @IntoMap
    @ViewModelKey(ReposFragmentViewModel::class)
    fun bindReposFragmentViewModel(vm: ReposFragmentViewModel): ViewModel

    companion object {

        @ReposScope
        @Provides
        fun provideGetReposUseCase(
            domainRepository: DomainRepository
        ): GetReposUseCase {
            return GetReposUseCase(domainRepository)
        }

        @ReposScope
        @Provides
        fun provideReposSubcomponentProvider(
            application: Application
        ): ReposSubcomponentProvider {
            return (application as ReposSubcomponentProvider)
        }
    }
}