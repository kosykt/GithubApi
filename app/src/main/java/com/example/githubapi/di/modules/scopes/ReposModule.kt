package com.example.githubapi.di.modules.scopes

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.domain.*
import com.example.githubapi.di.annotations.ReposScope
import com.example.githubapi.di.annotations.ViewModelKey
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
            dataSourceRepository: DataSourceRepository
        ): GetReposUseCase {
            return GetReposUseCase(dataSourceRepository)
        }

        @ReposScope
        @Provides
        fun provideReposSubcomponentProvider(
            application: Application
        ): ReposSubcomponentProvider {
            return (application as ReposSubcomponentProvider)
        }

        @ReposScope
        @Provides
        fun provideSaveFavouriteRepoUseCase(
            dataSourceRepository: DataSourceRepository
        ): SaveFavouriteRepoUseCase{
            return SaveFavouriteRepoUseCase(dataSourceRepository)
        }

        @ReposScope
        @Provides
        fun provideDeleteFavouriteRepoUseCase(
            dataSourceRepository: DataSourceRepository
        ): DeleteFavouriteRepoUseCase{
            return DeleteFavouriteRepoUseCase(dataSourceRepository)
        }

        @ReposScope
        @Provides
        fun provideGetAllFavouriteReposIdUseCase(
            dataSourceRepository: DataSourceRepository
        ): GetAllFavouriteReposIdUseCase{
            return GetAllFavouriteReposIdUseCase(dataSourceRepository)
        }
    }
}