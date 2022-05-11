package com.example.githubapi.di.components

import com.example.githubapi.di.annotations.UsersScope
import com.example.githubapi.di.modules.scopes.UsersModule
import com.example.githubapi.ui.usersfragment.UsersFragment
import dagger.Subcomponent

@UsersScope
@Subcomponent(modules = [UsersModule::class])
interface UsersSubcomponent {

    fun provideReposSubcomponent(): ReposSubcomponent
    fun inject(fragment: UsersFragment)
}