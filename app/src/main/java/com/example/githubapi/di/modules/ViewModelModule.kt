package com.example.githubapi.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.githubapi.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}