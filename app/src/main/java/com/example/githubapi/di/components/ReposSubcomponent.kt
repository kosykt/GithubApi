package com.example.githubapi.di.components

import com.example.githubapi.di.annotations.ReposScope
import com.example.githubapi.di.modules.scopes.ReposModule
import com.example.githubapi.ui.reposfragment.ReposFragment
import dagger.Subcomponent

@ReposScope
@Subcomponent(modules = [ReposModule::class])
interface ReposSubcomponent {

    fun inject(fragment: ReposFragment)
}